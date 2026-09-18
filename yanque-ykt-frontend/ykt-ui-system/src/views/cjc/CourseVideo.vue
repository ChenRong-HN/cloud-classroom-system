<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.keyword" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getTableData" size="small" icon="el-icon-search">查询视频</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="small" @click="addVideoHandler" icon="el-icon-video-camera">新增视频
          </el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="tableData" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="selection">
      </el-table-column>

      <el-table-column prop="courseName" label="课程">
      </el-table-column>
      <el-table-column prop="chapterName" label="章节">
      </el-table-column>
      <el-table-column prop="name" label="视频">
      </el-table-column>
      <el-table-column prop="fileOriginalName" label="文件名">
      </el-table-column>
      <el-table-column prop="fileStatus" label="状态" width="120" :formatter="formatState" sortable>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.row)" icon="el-icon-edit" type="primary">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.row)" icon="el-icon-remove">删除</el-button>

          <span v-if="scope.row.free">
						<el-button type="danger" size="small" @click="handleFree(scope.row)"
                       icon="el-icon-s-flag">取消试看</el-button>
					</span>
          <span v-else>
						<el-button type="success" size="small" @click="handleFree(scope.row)"
                       icon="el-icon-s-flag">设为试看</el-button>
					</span>
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


    <!--新增视频-->
    <el-dialog title="新增视频" :visible.sync="addVideoFormVisible" :close-on-click-modal="false" width="800px">
      <el-form :model="addVideoForm" label-width="80px" ref="addForm">
        <el-form-item label="所属课程" prop="courseId">
          <el-input v-model="addVideoForm.courseName" auto-complete="off" style="width: 550px" disabled></el-input>
          <el-button type="primary" @click="dialogTableVisible = true" icon="el-icon-search">选择课程</el-button>
        </el-form-item>

        <el-form-item label="章节名称" prop="name">
          <el-select @change="selectCourseChapter" v-model="addVideoForm.chapterId" placeholder="请选择"
                     style="width: 680px">
            <el-option
                v-for="item in courseChapters"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="视频名称" prop="name">
          <el-input v-model="addVideoForm.name" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="视频序号" prop="number">
          <el-input v-model="addVideoForm.number" type="number" min="1" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="视频上传" prop="name">
          <el-upload
              action=""
              accept="video/*"
              :auto-upload="false"
              :limit="1"
              :file-list="videoFileList"
              :on-change="handleVideoFileChange"
              :on-remove="handleVideoFileRemove">
            <el-button size="small" type="primary" icon="el-icon-upload">选择视频</el-button>
            <div slot="tip" class="el-upload__tip">选择视频后，点击提交将直传 OSS 并保存视频信息</div>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addVideoFormVisible = false" icon="el-icon-remove">取消</el-button>
        <el-button type="primary" @click.native="addVideo" :loading="uploadSubmitting" icon="el-icon-check">提交
        </el-button>
      </div>
    </el-dialog>


    <!--选择课程弹窗-->
    <el-dialog title="选择课程" :visible.sync="dialogTableVisible" :close-on-click-modal="false" width="800px">
      <el-form :model="courseAddForm" label-width="80px" ref="dialogTableAddForm">
        <!--工具条-->
        <el-form-item label-width="0px" style="border-bottom: 1px solid #eeeeee;padding-bottom: 20px">
          <el-input v-model="courseAddForm.keyword" placeholder="搜索课程名" style="width: 200px"
                    size="small"></el-input>
          <el-button type="warning" @click="getCourses(1)" icon="el-icon-search" size="small">查询</el-button>
          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">搜索课程后加载数据，双击行进行选择</span>
        </el-form-item>

        <el-form-item label-width="0px">
          <el-table :data="courses" highlight-current-row @row-dblclick="selectCourse">
            <el-table-column property="id" label="ID" width="100"></el-table-column>
            <el-table-column property="name" label="课程"></el-table-column>
            <el-table-column property="gradeName" label="等级"></el-table-column>
          </el-table>
        </el-form-item>

        <el-form-item label-width="0px">
          <el-pagination layout="prev, pager, next" :page-size="10" :current-page="courseAddForm.page"
                         @current-change="getCourses" :total="courseTotal" style="float:right;">
          </el-pagination>
        </el-form-item>
      </el-form>
    </el-dialog>


  </section>
</template>

<script>

export default {
  data() {
    return {
      filters: {
        keyword: ''
      },
      tableData: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],
      //添加章节视频的数据
      addVideoForm: {
        id: null,
        chapterId: "",
        chapterName: '',
        courseId: '',
        number: null,
        name: '',
        courseName: '',
        objectKey: '',
        fileOriginalName: '',
        fileType: '',
        fileSize: null,
        uploadTime: null,
        timeMinute: null,
        free: 0
      },
      addVideoFormVisible: false,
      videoFile: null,
      videoFileList: [],
      uploadSubmitting: false,
      //选择课程=========================
      dialogTableVisible: false,
      courseAddForm: {
        keyword: '',
        page: 1
      },
      courseTotal: 0,
      courses: [],
      //选择章节===================
      courseChapters: []
    }

  },
  methods: {
    addVideoFormVisibleClose() {
      this.addVideoFormVisible = false;
    },
    addVideoHandler() {
      this.resetAddVideoForm();
      this.addVideoFormVisible = true;
    },
    handleVideoFileChange(file, fileList) {
      this.videoFile = file.raw;
      this.videoFileList = fileList.slice(-1);
      if (!this.addVideoForm.name && this.videoFile) {
        this.addVideoForm.name = this.videoFile.name.replace(/\.[^.]+$/, '');
      }
      if (this.videoFile) {
        this.readVideoDuration(this.videoFile);
      }
    },
    handleVideoFileRemove() {
      this.videoFile = null;
      this.videoFileList = [];
    },
    readVideoDuration(file) {
      const video = document.createElement('video');
      const objectUrl = URL.createObjectURL(file);
      video.preload = 'metadata';
      video.onloadedmetadata = () => {
        this.addVideoForm.timeMinute = Math.max(1, Math.ceil(video.duration / 60));
        URL.revokeObjectURL(objectUrl);
      };
      video.onerror = () => URL.revokeObjectURL(objectUrl);
      video.src = objectUrl;
    },
    getFileSuffix(fileName) {
      const index = fileName.lastIndexOf('.');
      return index > -1 ? fileName.substring(index + 1).toLowerCase() : 'bin';
    },
    async addVideo() {
      if (!this.addVideoForm.courseId) {
        return this.$message({message: '请选择课程', type: 'warning'});
      }
      if (!this.addVideoForm.chapterId) {
        return this.$message({message: '请选择章节', type: 'warning'});
      }
      if (!this.addVideoForm.name) {
        return this.$message({message: '请输入视频名称', type: 'warning'});
      }
      if (!this.videoFile && !this.addVideoForm.objectKey) {
        return this.$message({message: '请选择需要上传的视频文件', type: 'warning'});
      }

      this.uploadSubmitting = true;
      try {
        if (this.videoFile) {
          const contentType = this.videoFile.type || 'application/octet-stream';
          const dir = `course-video/${this.addVideoForm.courseId}/${this.addVideoForm.chapterId}`;
          const preSignResponse = await this.$http.get('/common/oss/getPreSignUpload', {
            params: {
              dir,
              fileSuffix: this.getFileSuffix(this.videoFile.name),
              contentType
            }
          });
          if (!preSignResponse.data.success || !preSignResponse.data.data) {
            throw new Error(preSignResponse.data.message || '获取上传地址失败');
          }

          const preSignData = preSignResponse.data.data;
          const uploadUrl = preSignData.preSignUoLoadUrl;
          if (!uploadUrl || !preSignData.ossObjectKey) {
            throw new Error('预签名接口返回数据不完整');
          }

          const uploadResponse = await fetch(uploadUrl, {
            method: 'PUT',
            headers: {'Content-Type': contentType},
            body: this.videoFile
          });
          if (!uploadResponse.ok) {
            throw new Error(`OSS上传失败（HTTP ${uploadResponse.status}）`);
          }

          this.addVideoForm.objectKey = preSignData.ossObjectKey;
          this.addVideoForm.fileOriginalName = this.videoFile.name;
          this.addVideoForm.fileType = contentType;
          this.addVideoForm.fileSize = this.videoFile.size;
          this.addVideoForm.uploadTime = new Date().toISOString();
        }

        const saveResponse = await this.$http.post('/media/mediaFile/save', this.addVideoForm);
        if (!saveResponse.data.success) {
          throw new Error(saveResponse.data.message || '保存视频信息失败');
        }
        this.$message({message: '视频上传成功', type: 'success'});
        this.addVideoFormVisible = false;
        this.resetAddVideoForm();
        this.getTableData();
      } catch (error) {
        this.$message({message: error.message || '视频上传失败', type: 'error'});
      } finally {
        this.uploadSubmitting = false;
      }
    },
    resetAddVideoForm() {
      this.addVideoForm = {
        id: null,
        chapterId: '',
        chapterName: '',
        courseId: '',
        number: null,
        name: '',
        courseName: '',
        objectKey: '',
        fileOriginalName: '',
        fileType: '',
        fileSize: null,
        uploadTime: null,
        timeMinute: null,
        free: 0
      };
      this.videoFile = null;
      this.videoFileList = [];
      this.courseChapters = [];
    },
    //加载课程==============================
    getCourses(currentPage = 1) {
      this.courseAddForm.page = currentPage;
      this.$http.post("/course/course/pagelist", this.courseAddForm).then(result => {
        this.courses = result.data.data.rows;
        this.courseTotal = result.data.data.total;
      });
    },
    selectCourse(row) {
      this.courseAddForm.keyword = "";
      this.addVideoForm.courseId = row.id;
      this.addVideoForm.courseName = row.name;
      this.dialogTableVisible = false;
      this.getCourseChapter();
    },

    //加载章节
    getCourseChapter() {
      if (this.addVideoForm.courseId) {
        this.$http.get("/course/courseChapter/listByCourseId/" + this.addVideoForm.courseId).then(result => {
          this.courseChapters = result.data.data;
        });
      }
    },
    //选择章节
    selectCourseChapter(courseChapterId) {
      if (courseChapterId) {
        for (let i = 0; i < this.courseChapters.length; i++) {
          let courseChapter = this.courseChapters[i];
          console.log(courseChapter);
          if (courseChapter.id === courseChapterId) {
            this.addVideoForm.chapterName = courseChapter.name;
            break;
          }
        }
      }
    },
    //试看start==============================
    handleFree(row) {
      this.$http.post("/media/mediaFile/update2Free/" + row.id).then(result => {
        let {success, message} = result.data;
        if (success) {
          this.$message({
            message: '设置成功',
            type: 'success'
          });
          this.getTableData();
        } else {
          this.$message({
            message: message,
            type: 'error'
          });
        }
      }).catch(error => {
        this.$message({
          message: '设置失败[' + error + ']',
          type: 'error'
        });
      });
    },
    //试看end==============================
    //性别显示转换
    formatState: function (row, column) {
      //1未处理 2处理成功 3处理失败 4无需处理
      if (row.fileStatus == 1) {
        return '处理中...';
      }
      if (row.fileStatus == 2) {
        return '上传成功';
      }
      if (row.fileStatus == 3) {
        return '上传失败';
      }
      if (row.fileStatus == 4) {
        return '无需处理';
      }
      return '未知';
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getTableData();
    },
    //获取用户列表
    getTableData() {
      let para = {
        page: this.page,
        keyword: this.filters.keyword
      };
      this.listLoading = true; //显示加载圈
      this.$http.post("/media/mediaFile/pagelist", para).then(result => {
        this.total = result.data.data.total;
        this.tableData = result.data.data.rows;
        this.listLoading = false;  //关闭加载圈
      });
    },
    selsChange(sels) {
      this.sels = sels;
    },
    batchRemove() {
      this.$message({message: "功能未开放", type: 'error'});
    },
  },
  mounted() {
    this.getTableData();
  }
}

</script>

<style scoped>

</style>
