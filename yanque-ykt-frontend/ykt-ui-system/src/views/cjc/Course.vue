<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :model="filters" :inline="true">
        <el-form-item>
          <el-input v-model="filters.keywords" size="small" placeholder="关键字"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="warning" v-on:click="getCourses" size="small" icon="el-icon-search">查询课程</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addHandler" size="small" icon="el-icon-notebook-1">新增课程</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="success" @click="onLineCourse" size="small" icon="el-icon-s-promotion">课程发布</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="offLineCourse" size="small" icon="el-icon-download">课程下架</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表v-loading="listLoading"-->
    <el-table :data="courses" v-loading="listLoading" @selection-change="selsChange"
              highlight-current-row style="width: 100%;">
      <!--多选框-->
      <el-table-column type="selection" width="55e">
      </el-table-column>
      <!--其他都设置值,只有一个不设置值就自动适应了-->
      <el-table-column prop="name" label="课程名称">
      </el-table-column>
      <el-table-column prop="chapterCount" label="章节数">
      </el-table-column>
      <!--<el-table-column prop="courseType.name" label="类型">-->
      <!--</el-table-column>-->
      <el-table-column prop="gradeName" label="等级">
      </el-table-column>
      <el-table-column prop="status" label="状态" :formatter="statusFormatter">
      </el-table-column>
      <el-table-column prop="forUser" label="适用人群" width="220">
      </el-table-column>
      <!--<el-table-column prop="tenantName" label="所属机构">-->
      <!--</el-table-column>-->
      <el-table-column prop="teacherNames" label="讲师" width="140">
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template scope="scope">
          <el-button size="small" @click="edit(scope.row)" icon="el-icon-edit" type="primary">编辑</el-button>
          <el-button type="danger" size="small" @click="del(scope.row)" icon="el-icon-remove">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0" icon="el-icon-remove" size="small">
        批量删除
      </el-button>
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--新增界面-->
    <el-dialog title="新增" :visible.sync="addFormVisible" :close-on-click-modal="false" width="860px">
      <el-form :inline="true" :model="addForm" label-width="80px" ref="addForm">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="addForm.name" placeholder="课程名称" auto-complete="off" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="适用人群" prop="forUser">
          <el-input v-model="addForm.forUser" placeholder="适用人群" auto-complete="off" style="width: 300px"/>
        </el-form-item>
        <el-form-item label="课程类型" prop="courseTypeId">
          <el-cascader style="width: 300px"
                       :props="courseTypeProps"
                       v-model="addForm.courseTypeId"
                       placeholder="课程类型"
                       :options="courseTypes"
                       expand-trigger="hover"
                       :show-all-levels="false"
                       filterable
                       change-on-select
          ></el-cascader>
        </el-form-item>
        <el-form-item label="添加讲师" prop="teachers">
          <el-select v-model="addForm.teacharIds" multiple placeholder="可选多个讲师" style="width: 300px">
            <el-option
                v-for="item in teachers"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="课程周期">
          <el-date-picker style="width: 200px"
                          v-model="addForm.startTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          size="small"
                          placeholder="课程开始日期">
          </el-date-picker>
          -
          <el-date-picker style="width: 200px"
                          v-model="addForm.endTime"
                          type="date"
                          value-format="yyyy-MM-dd"
                          size="small"
                          placeholder="课程结束日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="购买可看">
          <el-input placeholder="可看天数" type="number" v-model="addForm.validDays" auto-complete="off"
                    style="width: 165px"/>&nbsp;天
        </el-form-item>

        <el-form-item label="课程等级" prop="courseTypeId" style="width: 700px">
          <el-radio-group v-model="addForm.gradeId">
            <el-radio v-for="grade in grades" :label="grade.id">{{ grade.name }}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item prop="logo" style="width: 400px">
          <!--<el-input type="text" v-model="employee.logo" auto-complete="off" placeholder="请输入logo！"></el-input>-->
          <el-upload
              ref="picUpload"
              class="upload-demo"
              list-type="picture"
              :action="'#'"
              :http-request="uploadCover"
              :limit="1">
            <el-button size="small" type="primary" icon="el-icon-picture-outline">上传封面</el-button>
            &nbsp;&nbsp;<span slot="tip" class="el-upload__tip">支持500kb，格式jpg</span>
          </el-upload>
        </el-form-item>

        <el-form-item prop="logo">
          <!--<el-input type="text" v-model="employee.logo" auto-complete="off" placeholder="请输入logo！"></el-input>-->
          <el-upload
              ref="resourceUpload"
              class="upload-demo"
              :action="'#'"
              :http-request="uploadCourseware"
              :limit="1">
            <el-button size="small" type="primary" icon="el-icon-upload">上传课件</el-button>
            &nbsp;&nbsp;<span slot="tip" class="el-upload__tip">支持压缩格式</span>
          </el-upload>
        </el-form-item>
        <el-divider></el-divider>

        <el-form-item label="收费规则" prop="gradeId" size="width:100%">
          <el-radio-group v-model="addForm.chargeId">
            <el-radio @change="changeCharge" v-for="charge in charges" :label="charge.id">{{ charge.name }}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="课程价格" prop="price">
          <el-input :disabled="priceDisabled" type="number" v-model="addForm.price" auto-complete="off"
                    style="width: 185px"/>
        </el-form-item>
        <el-form-item label="课程原价">
          <el-input :disabled="priceDisabled" type="number" v-model="addForm.priceOld" auto-complete="off"
                    style="width: 185px"/>
        </el-form-item>

        <el-form-item label="咨询QQ" prop="qq">
          <el-input v-model="addForm.qq" auto-complete="off" style="width: 150px"></el-input>
        </el-form-item>


        <el-form-item label="课程简介" prop="description">
          <el-input style="width: 450px"
                    :rows="2"
                    placeholder="请输入内容"
                    v-model="addForm.description">
          </el-input>
        </el-form-item>

        <el-form-item label="课程详情" prop="intro">
          <div class="edit_container">
            <quill-editor
                v-model="addForm.intro"
                ref="myQuillEditor"
                class="editer"
                :options="editorOption"
                @ready="onEditorReady($event)">
            </quill-editor>
          </div>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false" icon="el-icon-remove">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" icon="el-icon-check">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import {quillEditor} from "vue-quill-editor"; //调用编辑器
import "quill/dist/quill.core.css"
import "quill/dist/quill.snow.css"
import "quill/dist/quill.bubble.css"

export default {
  computed: {
    editor() {
      return this.$refs.myQuillEditor.quill
    }
  },
  components: {//使用编辑器
    quillEditor
  },
  data() {
    return {
      row: "",
      courseTypeProps: {
        value: "id",
        label: "name"
      },
      priceDisabled: true,
      editorOption: {},//富文本编辑框配置
      grades: [
        {id: 1, name: "青铜"}, {id: 2, name: "白银"}, {id: 3, name: "黄金"}, {id: 4, name: "白金"}, {
          id: 5,
          name: "钻石"
        }
      ],
      charges: [
        {"id": 1, "name": "免费"},
        {"id": 2, "name": "收费"}
      ],
      teachers: [],
      courseTypes: [],
      addFormVisible: false,
      //images:[xxx.jgp,xxxx,jpg,xxxx.jpg],
      addForm: {
        startTime: '',
        endTime: '',
        validDays: '',
        name: '',
        forUser: '',
        gradeId: '',
        teacharIds: [],
        courseTypeId: [],
        description: '',
        intro: '',
        chargeId: '',
        price: '',
        priceOld: '',
        qq: '',
        pic: '',
        zipResources: ''
      },
      listLoading: false,
      //查询对象
      filters: {
        keywords: ''
      },
      page: 1,//当前页,要传递到后台的
      total: 0, //分页总数
      courses: [], //当前页数据
      sels: '',
    }
  },
  methods: {
    /*file是当前文件对象 data要上传到oss的其它参数*/
    uploadCover(option) {
      return this.uploadToOss(option, 'course/cover', 'pic', '封面');
    },
    uploadCourseware(option) {
      return this.uploadToOss(option, 'course/courseware', 'zipResources', '课件');
    },
    async uploadToOss(option, dir, fieldName, fileLabel) {
      const file = option.file;
      const suffixIndex = file.name.lastIndexOf('.');
      const fileSuffix = suffixIndex >= 0 ? file.name.substring(suffixIndex + 1) : '';

      if (!fileSuffix) {
        const error = new Error('文件必须包含扩展名');
        option.onError(error);
        this.$message({message: error.message, type: 'error'});
        return;
      }

      try {
        const response = await this.$http.get('/common/oss/getPreSignUpload', {
          params: {
            dir: dir,
            fileSuffix: fileSuffix,
            contentType: file.type || 'application/octet-stream'
          }
        });
        const ajaxResult = response.data;
        if (!ajaxResult.success || !ajaxResult.data) {
          throw new Error(ajaxResult.message || '获取上传预签名失败');
        }

        const preSign = ajaxResult.data;
        const uploadResponse = await fetch(preSign.preSignUoLoadUrl, {
          method: 'PUT',
          headers: {
            'Content-Type': file.type || 'application/octet-stream'
          },
          body: file
        });
        if (!uploadResponse.ok) {
          throw new Error(`OSS 上传失败（${uploadResponse.status}）`);
        }

        this.addForm[fieldName] = preSign.ossObjectKey;
        option.onSuccess({ossObjectKey: preSign.ossObjectKey}, file);
        this.$message({message: `${fileLabel}上传成功`, type: 'success'});
      } catch (error) {
        option.onError(error);
        this.$message({message: error.message || `${fileLabel}上传失败`, type: 'error'});
      }
    },
    addSubmit() {
      const courseTypeId = Array.isArray(this.addForm.courseTypeId)
          ? this.addForm.courseTypeId[this.addForm.courseTypeId.length - 1]
          : this.addForm.courseTypeId;
      if (!courseTypeId) {
        this.$message({message: '请选择课程类型', type: 'warning'});
        return;
      }
      var gradeName;
      for (var i = 0; i < this.grades.length; i++) {
        var grade = this.grades[i];
        if (grade.id === this.addForm.gradeId) {
          gradeName = grade.name;
          break;
        }
      }

      var param = {
        course: {
          courseTypeId: courseTypeId,
          name: this.addForm.name,
          forUser: this.addForm.forUser,
          gradeId: this.addForm.gradeId,
          gradeName: gradeName,
          pic: this.addForm.pic,
          startTime: this.addForm.startTime,
          endTime: this.addForm.endTime
        },
        courseDetail: {
          description: this.addForm.description,
          intro: this.addForm.intro
        },
        courseMarket: {
          charge: this.addForm.chargeId,
          qq: this.addForm.qq,
          price: this.addForm.price,
          priceOld: this.addForm.priceOld,
          validDays: this.addForm.validDays
        },
        courseResource: {
          resources: this.addForm.zipResources,
          type: 0	//课件
        },
        teacharIds: this.addForm.teacharIds
      };

      this.$http.post("/course/course/save_course", param).then(res => {
        var ajaxResult = res.data;
        if (ajaxResult.success) {
          this.$message({
            message: '保存成功!',
            type: 'success'
          });
          this.addFormVisible = false;
          this.resetAddForm();
          this.getCourses();
        } else {
          this.$message({
            message: '提交失败[' + res.data.message + "]",
            type: 'error'
          });
        }
      });
    },
    getGrades() {
      this.$http.get("/system/systemdictionaryitem/listBySn/dj").then(result => {
        this.grades = result.data.data;
      });
    },
    getCourseTypes() {
      this.$http.get("/course/courseType/treeData").then(result => {
        this.courseTypes = result.data.data;
      });
    },
    changeCharge(chargeId) {
      if (chargeId === 1) {
        this.priceDisabled = true;
        this.addForm.price = "";
        this.addForm.priceOld = "";
      } else {
        this.priceDisabled = false;
      }
    },
    onEditorReady(editor) {
      //当富文本编辑框初始化好执行
    },
    createEmptyAddForm() {
      return {
        startTime: '',
        endTime: '',
        validDays: '',
        name: '',
        forUser: '',
        gradeId: '',
        teacharIds: [],
        courseTypeId: [],
        description: '',
        intro: '',
        chargeId: '',
        price: '',
        priceOld: '',
        qq: '',
        pic: '',
        zipResources: ''
      };
    },
    resetAddForm() {
      this.addForm = this.createEmptyAddForm();
      this.priceDisabled = true;
      this.$nextTick(() => {
        if (this.$refs.addForm) {
          this.$refs.addForm.clearValidate();
        }
        if (this.$refs.picUpload) {
          this.$refs.picUpload.clearFiles();
        }
        if (this.$refs.resourceUpload) {
          this.$refs.resourceUpload.clearFiles();
        }
      });
    },
    addHandler() {
      this.resetAddForm();
      this.addFormVisible = true;
    },
    handleCurrentChange(curentPage) {
      this.page = curentPage;
      this.getCourses();
    },
    getCourses() {
      //发送Ajax请求后台获取数据  axios
      //添加分页条件及高级查询条件
      let para = {
        "page": this.page,
        "keyword": this.filters.keywords
      };
      this.listLoading = true; //显示加载圈
      //分页查询
      this.$http.post("/course/course/pagelist", para).then(result => {
        this.total = result.data.data.total;
        this.courses = result.data.data.rows;
        this.listLoading = false;  //关闭加载圈
      }).catch(error => {
        this.$message({message: error.message, type: 'error'});
      });
    },
    onLineCourse() {
      // 有选中的
      if (this.sels && this.sels.length > 0) {
        // 取选中的 courseId 数组
        const courseIds = this.sels.map(item => item.id);
        console.log(courseIds);

        this.$http.post("/course/course/batchOnLine", courseIds).then(res => {
          var ajaxResult = res.data;
          if (ajaxResult.success) {
            this.$message({message: '老铁，上线成功.', type: 'success'});
            this.getCourses();
          } else {
            this.$message({message: ajaxResult.message, type: 'error'});
          }
          this.sels = "";
          this.row = "";
        }).catch(error => {
          this.$message({message: error.message, type: 'error'});
        });

      } else {
        this.$message({message: '老铁，你不选中数据，臣妾上不了啊....', type: 'error'});
      }

      /*if(!this.row || this.row  === ""){
                  this.$message({ message: '老铁，你不选中数据，臣妾上不了啊....',type: 'error'});
          return;
      }*/

      /**
       * 1. this.row: 是一个集合  [{"id":1,"name":"xx"},{"id":2,"name":"yy"}]
       *
       *  批量操作： 获取所有的id，发送给后端
       *
       *  获取一个id，发送给后端
       *
       */
      // 获取this.row中的所有id
      /* var arrId = [];
       for(var i = 0 ; i < this.row.length ; i++){
           arrId.push(this.row[i].id);
       }
       console.debug(arrId);
 this.$http.post("/course/course/onLineCourse",arrId).then(res=>{
     var ajaxResult = res.data;
     if(ajaxResult.success){
         this.$message({ message: '老铁，上线成功.',type: 'success'});
         this.getCourses();
     }else{
         this.$message({ message: ajaxResult.message,type: 'error'});
     }
   // 清空
   arrId.length = 0;
 }).catch(error => {
   this.$message({ message: error.message,type: 'error'});
 });*/

    },
    // offLineCourse() {
    //   //获取选中的行
    //   if (!this.row || this.row === "") {
    //     this.$message({message: '老铁，你不选中数据，臣妾下不了啊....', type: 'error'});
    //     return;
    //   }
    //   console.log("888888888888888888888")
    //   console.log(this.row)
    //   this.$http.post("/course/course/offLineCourse/" + this.row[0].id).then(res => {
    //     var ajaxResult = res.data;
    //     if (ajaxResult.success) {
    //       this.$message({message: '老铁，下线成功.', type: 'success'});
    //       this.getCourses();
    //     } else {
    //       this.$message({message: ajaxResult.message, type: 'error'});
    //     }
    //   })
    // },
    offLineCourse() {
      //获取选中的行
      if (!this.row || this.row === "") {
        this.$message({message: '老铁，你不选中数据，臣妾下不了啊....', type: 'error'});
        return;
      }
      console.log("888888888888888888888")
      console.log(this.row)
      const courseIds = this.row.map(item => item.id);
      this.$http.post("/course/course/offLineCourse", courseIds).then(res => {
        var ajaxResult = res.data;
        if (ajaxResult.success) {
          this.$message({message: '老铁，下线成功.', type: 'success'});
          this.getCourses();
        } else {
          this.$message({message: ajaxResult.message, type: 'error'});
        }
      })
    },
    statusFormatter: function (row, column) {
      return row.status == 1 ? '已上线' : '未上线';
    },

    //讲师
    getTeachers() {
      this.$http.get("/course/teacher/list")
          .then(result => {
            this.teachers = result.data.data;
            console.log(this.teachers)
          }).catch(error => {
        this.$message({message: error.message, type: 'error'});
      });
    },
    edit() {
      this.$message({message: "功能未开放", type: 'error'});
    },
    del() {
      this.$message({message: "功能未开放", type: 'error'});
    },
    batchRemove() {
      this.$message({message: "功能未开放", type: 'error'});
    },
    selsChange(sels) {
      console.log(sels);
      this.row = sels;
      this.sels = sels;
    },
  },

  mounted() {
    this.getCourses();
    //this.getGrades();
    this.getCourseTypes();
    this.getTeachers();
  }
}

</script>

<style scoped>

</style>
