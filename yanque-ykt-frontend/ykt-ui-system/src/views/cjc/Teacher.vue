<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="filters">
				<el-form-item>
					<el-input v-model="filters.keyword" placeholder="关键字" size="small"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getTableData" icon="el-icon-search" size="small">执行查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary"  icon="el-icon-plus" size="small"  v-on:click="addTeacher" >讲师新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="tableData" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column  label="头像">
				<template scope="scope">
					<el-image style="width: 40px;height: 40px"
							:src="scope.row.headImg"></el-image>
				</template>
			</el-table-column>
			<el-table-column prop="name" label="名称" >
			</el-table-column>
			<el-table-column prop="position" label="职位"  >
			</el-table-column>
			<el-table-column prop="tags" label="标签"  >
			</el-table-column>
			<el-table-column prop="intro" label="介绍" width="200" >
				<template slot-scope="scope">
					<el-popover trigger="hover" placement="top" >
						<div style="width: 400px;">{{ scope.row.intro }}</div>
						<div slot="reference" class="name-wrapper" >
							<el-tag >
								{{ scope.row.intro }}
							</el-tag>
						</div>
					</el-popover>
				</template>
			</el-table-column>
			<el-table-column label="操作" width="200">
				<template scope="scope">
					<el-button size="small"  @click="edit(scope.row)" icon="el-icon-edit" type="primary">编辑</el-button>
					<el-button type="danger" size="small" @click="del(scope.row)" icon="el-icon-remove">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0" icon="el-icon-remove" size="small">批量删除</el-button>
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--新增/编辑界面(新增与编辑共用同一个表单,由dialogTitle区分)-->
		<el-dialog :title="dialogTitle" :visible.sync="addFormVisible" :close-on-click-modal="false" width="600px">
			<!--各字段maxlength与t_teacher表的varchar(255)保持一致,避免超长被数据库拒绝-->
			<el-form :model="addForm" :rules="rules" label-width="80px" ref="addForm">
				<el-form-item label="讲师姓名" prop="name">
					<el-input v-model="addForm.name" placeholder="讲师姓名" :maxlength="255" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="职位" prop="position">
					<el-input v-model="addForm.position" placeholder="职位" :maxlength="255" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="技术栈" prop="technology">
					<el-input v-model="addForm.technology" placeholder="技术栈" :maxlength="255" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="标签" prop="tags">
					<el-input v-model="addForm.tags" placeholder="多个标签用逗号分隔" :maxlength="255" auto-complete="off"></el-input>
				</el-form-item>
				<el-form-item label="头像" prop="headImg">
					<!--头像走阿里云Oss预签名直传,file-list用于编辑时回显原图,删除后可重新上传-->
					<el-upload
						ref="headImgUpload"
						class="upload-demo"
						list-type="picture"
						:action="'#'"
						:http-request="uploadHeadImg"
						:file-list="headImgFileList"
						:on-remove="handleHeadImgRemove"
						:on-exceed="handleHeadImgExceed"
						:limit="1">
						<el-button size="small" type="primary" icon="el-icon-picture-outline">上传头像</el-button>
						&nbsp;&nbsp;<span slot="tip" class="el-upload__tip">支持500kb，格式jpg/png</span>
					</el-upload>
				</el-form-item>
				<el-form-item label="简介" prop="intro">
					<el-input type="textarea" :rows="3" v-model="addForm.intro" placeholder="讲师简介" :maxlength="255" show-word-limit></el-input>
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
				sels:[],
				//新增/编辑对话框显示开关
				addFormVisible: false,
				//对话框标题(新增讲师/编辑讲师)
				dialogTitle: '新增讲师',
				//新增与编辑共用的表单数据
				addForm: this.createEmptyAddForm(),
				//头像上传组件的文件列表(编辑时用于回显原图)
				headImgFileList: [],
				//表单校验规则:姓名、职位、头像为必填项
				rules: {
					name: [
						{required: true, message: '请输入讲师姓名', trigger: 'blur'},
						{max: 255, message: '讲师姓名不能超过255个字符', trigger: 'blur'}
					],
					position: [
						{required: true, message: '请输入讲师职位', trigger: 'blur'},
						{max: 255, message: '讲师职位不能超过255个字符', trigger: 'blur'}
					],
					headImg: [
						{required: true, message: '请上传讲师头像', trigger: 'change'}
					]
				}
			}

		},
		methods: {
			selsChange(sels){
				this.sels = sels;
			},
			//性别显示转换
			formatState: function (row, column) {
				return row.sex == 1 ? '禁用' : '启用';
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
				this.$http.post("/course/teacher/pagelist",para).then(result=>{
					this.total = result.data.data.total;
					this.tableData = result.data.data.rows;
					this.listLoading = false;  //关闭加载圈
				});
			},
			//构造空白表单对象,避免新增与编辑的数据互相污染
			createEmptyAddForm(){
				return {
					id: null,
					name: '',
					position: '',
					technology: '',
					tags: '',
					headImg: '',
					intro: ''
				};
			},
			//清除上一次遗留的校验错误提示(关闭对话框后错误状态会保留)
			clearFormValidate(){
				this.$nextTick(() => {
					if (this.$refs.addForm) {
						this.$refs.addForm.clearValidate();
					}
				});
			},
			//讲师新增:打开空白表单
			addTeacher(){
				this.dialogTitle = '新增讲师';
				this.addForm = this.createEmptyAddForm();
				//新增时上传组件不带任何文件
				this.headImgFileList = [];
				this.addFormVisible = true;
				this.clearFormValidate();
			},
			//讲师编辑:打开表单并回显当前行数据
			edit(row){
				this.dialogTitle = '编辑讲师';
				this.addForm = {
					id: row.id,
					name: row.name || '',
					position: row.position || '',
					technology: row.technology || '',
					tags: row.tags || '',
					headImg: row.headImg || '',
					intro: row.intro || ''
				};
				//编辑时把原头像放进文件列表,实现打开即预览;删除该文件后即可上传新头像
				this.headImgFileList = row.headImg
					? [{ name: '原头像', url: row.headImg, status: 'success' }]
					: [];
				this.addFormVisible = true;
				this.clearFormValidate();
			},
			//提交表单:先通过表单校验,再按主键走修改(PUT)或新增(POST)
			addSubmit(){
				this.$refs.addForm.validate(valid => {
					if (!valid) {
						//校验不通过时,各表单项下方已自动显示红色提示,这里直接中断提交
						return;
					}
					if (this.addForm.id) {
						this.$http.put("/course/teacher", this.addForm).then(res => {
							this.handleSubmitResult(res, '修改成功');
						}).catch(error => {
							this.$message({ message: error.message || '修改失败', type: 'error' });
						});
					} else {
						this.$http.post("/course/teacher/save_teacher", this.addForm).then(res => {
							this.handleSubmitResult(res, '新增成功');
						}).catch(error => {
							this.$message({ message: error.message || '新增失败', type: 'error' });
						});
					}
				});
			},
			//统一处理新增/修改的响应结果
			handleSubmitResult(res, successMessage){
				const ajaxResult = res.data;
				if (!ajaxResult.success) {
					this.$message({ message: ajaxResult.message, type: 'error' });
					return;
				}
				this.addFormVisible = false;
				this.$message({ message: successMessage, type: 'success' });
				this.getTableData();
			},
			//头像上传:委托给通用Oss直传方法
			uploadHeadImg(option){
				return this.uploadToOss(option, 'teacher/head', 'headImg', '头像');
			},
			//移除头像时同步清空表单地址,便于重新上传
			handleHeadImgRemove(){
				this.addForm.headImg = '';
			},
			//超出上传数量限制时给出提示
			handleHeadImgExceed(){
				this.$message({ message: '请先删除原头像再上传新头像', type: 'warning' });
			},
			/**
			 * 通用Oss直传:先向后端取预签名URL,再PUT直传文件到Oss,最后把可访问地址写回表单
			 *
			 * @param option    el-upload传入的上传选项对象
			 * @param dir       文件在Oss桶中的目录
			 * @param fieldName 需要回写访问地址的表单字段名
			 * @param fileLabel 用于提示文案的文件名称
			 */
			async uploadToOss(option, dir, fieldName, fileLabel){
				const file = option.file;
				const suffixIndex = file.name.lastIndexOf('.');
				if (suffixIndex < 0) {
					const error = new Error('文件必须包含扩展名');
					option.onError(error);
					this.$message({ message: error.message, type: 'error' });
					return;
				}
				//注意:此处保留后缀前面的点,否则Oss中的文件名会丢失扩展名
				const fileSuffix = file.name.substring(suffixIndex);
				try {
					//第一步:向后端换取预签名URL(签名有效期1小时)
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
					//第二步:拿着预签名URL以PUT方式直传Oss,文件不经过后端服务中转
					const uploadResponse = await fetch(preSign.preSignUoLoadUrl, {
						method: 'PUT',
						headers: {
							'Content-Type': file.type || 'application/octet-stream'
						},
						body: file
					});
					if (!uploadResponse.ok) {
						throw new Error(`Oss上传失败（${uploadResponse.status}）`);
					}
					//第三步:去掉预签名URL尾部的签名参数,得到可直接访问的完整地址
					const accessUrl = preSign.preSignUoLoadUrl.split('?')[0];
					this.addForm[fieldName] = accessUrl;
					//同步文件列表,让刚上传的缩略图立即显示出来
					this.headImgFileList = [{ name: file.name, url: accessUrl, status: 'success' }];
					//头像已就位,立即清掉"请上传讲师头像"的校验提示
					if (this.$refs.addForm) {
						this.$refs.addForm.validateField('headImg');
					}
					option.onSuccess({ ossObjectKey: preSign.ossObjectKey }, file);
					this.$message({ message: `${fileLabel}上传成功`, type: 'success' });
				} catch (error) {
					option.onError(error);
					this.$message({ message: error.message || `${fileLabel}上传失败`, type: 'error' });
				}
			},
			//删除单个讲师
			del(row){
				this.$confirm(`确定删除讲师“${row.name}”吗？`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					return this.$http.delete(`/course/teacher/${row.id}`);
				}).then(res => {
					const ajaxResult = res.data;
					if (!ajaxResult.success) {
						this.$message({ message: ajaxResult.message, type: 'error' });
						return;
					}
					this.$message({ message: '删除成功', type: 'success' });
					this.getTableData();
				}).catch(error => {
					//点击取消/关闭弹窗时不提示错误
					if (error !== 'cancel' && error !== 'close') {
						this.$message({ message: error.message || '删除失败', type: 'error' });
					}
				});
			},
			//批量删除:循环调用单个删除接口
			batchRemove(){
				if (!this.sels || this.sels.length === 0) {
					this.$message({ message: '请先选择要删除的讲师', type: 'warning' });
					return;
				}
				this.$confirm(`确定删除选中的 ${this.sels.length} 位讲师吗？`, '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					return Promise.all(this.sels.map(row => this.$http.delete(`/course/teacher/${row.id}`)));
				}).then(() => {
					this.$message({ message: '删除成功', type: 'success' });
					this.sels = [];
					this.getTableData();
				}).catch(error => {
					if (error !== 'cancel' && error !== 'close') {
						this.$message({ message: error.message || '删除失败', type: 'error' });
					}
				});
			}

		},
		mounted() {
			this.getTableData();
		}
	}

</script>

<style scoped>

</style>