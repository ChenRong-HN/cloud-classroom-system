<template>
  <section>
    <el-form :inline="true" :model="query" @submit.native.prevent="search"
      ><el-form-item
        ><el-input
          v-model.trim="query.keyword"
          clearable
          placeholder="请输入关键字"
          @keyup.enter.native="search" /></el-form-item
      ><el-form-item
        ><el-button type="primary" icon="el-icon-search" @click="search"
          >查询</el-button
        ><el-button icon="el-icon-refresh" @click="resetSearch">重置</el-button
        ><el-button type="success" icon="el-icon-plus" @click="add"
          >新增</el-button
        ></el-form-item
      ></el-form
    >
    <el-table v-loading="loading" :data="tableData" border stripe
      ><el-table-column
        type="index"
        label="#"
        width="55"
        align="center"
      /><el-table-column prop="realName" label="姓名" /><el-table-column
        prop="tel"
        label="电话"
      /><el-table-column
        prop="email"
        label="邮箱"
        min-width="160"
      /><el-table-column label="部门"
        ><template slot-scope="scope">{{
          departmentName(scope.row.deptId)
        }}</template></el-table-column
      ><el-table-column label="员工类型"
        ><template slot-scope="scope">{{
          typeName(scope.row.type)
        }}</template></el-table-column
      ><el-table-column label="状态" width="90"
        ><template slot-scope="scope"
          ><el-tag
            :type="
              scope.row.state === 0
                ? 'success'
                : scope.row.state === 1
                ? 'warning'
                : 'info'
            "
            size="small"
            >{{ stateName(scope.row.state) }}</el-tag
          ></template
        ></el-table-column
      ><el-table-column
        prop="inputTime"
        label="入职时间"
        min-width="180"
        show-overflow-tooltip
      /><el-table-column label="操作" width="190" fixed="right" align="center"
        ><template slot-scope="scope"
          ><div class="operation-buttons">
            <el-button
              size="mini"
              type="primary"
              icon="el-icon-edit"
              @click="edit(scope.row)"
              >编辑</el-button
            ><el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              @click="del(scope.row)"
              >删除</el-button
            >
          </div></template
        ></el-table-column
      ></el-table
    >
    <el-pagination
      class="pager"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :current-page="query.page"
      :page-size="query.rows"
      :page-sizes="[10, 20, 50, 100]"
      :total="total"
      @current-change="changePage"
      @size-change="changeSize"
    />
    <el-dialog
      :title="form.id ? '编辑员工' : '新增员工'"
      :visible.sync="dialogVisible"
      width="560px"
      :close-on-click-modal="false"
      ><el-form ref="form" :model="form" :rules="rules" label-width="100px"
        ><el-form-item label="姓名" prop="realName"
          ><el-input
            v-model.trim="form.realName"
            maxlength="50" /></el-form-item
        ><el-form-item label="电话" prop="tel"
          ><el-input v-model.trim="form.tel" /></el-form-item
        ><el-form-item label="邮箱" prop="email"
          ><el-input v-model.trim="form.email" /></el-form-item
        ><el-form-item label="所属部门" prop="deptId"
          ><el-select
            v-model="form.deptId"
            clearable
            filterable
            style="width: 100%"
            ><el-option
              v-for="item in departments"
              :key="item.id"
              :label="item.name"
              :value="item.id" /></el-select></el-form-item
        ><el-form-item label="员工类型" prop="type"
          ><el-select v-model="form.type" style="width: 100%"
            ><el-option
              v-for="item in typeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value" /></el-select></el-form-item
        ><el-form-item label="状态" prop="state"
          ><el-select v-model="form.state" style="width: 100%"
            ><el-option
              v-for="item in stateOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value" /></el-select></el-form-item
        ><el-form-item label="入职时间" prop="inputTime"
          ><el-date-picker
            v-model="form.inputTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 100%" /></el-form-item
        ><el-form-item label="登录账号ID" prop="loginId"
          ><el-input-number
            v-model="form.loginId"
            :min="0"
            style="width: 100%" /></el-form-item></el-form
      ><span slot="footer"
        ><el-button @click="dialogVisible = false">取消</el-button
        ><el-button type="primary" :loading="saving" @click="save"
          >保存</el-button
        ></span
      ></el-dialog
    >
  </section>
</template>
<script>
export default {
  data() {
    return {
      query: { page: 1, rows: 10, keyword: "" },
      tableData: [],
      departments: [],
      total: 0,
      loading: false,
      saving: false,
      dialogVisible: false,
      form: {},
      stateOptions: [
        { value: 0, label: "正常" },
        { value: 1, label: "锁定" },
        { value: 2, label: "注销" },
      ],
      typeOptions: [
        { value: 1, label: "普通员工" },
        { value: 2, label: "客服人员" },
        { value: 3, label: "平台管理员" },
        { value: 4, label: "机构员工" },
        { value: 5, label: "机构管理员/其他" },
      ],
      rules: {
        realName: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        tel: [
          {
            pattern: /^1\d{10}$/,
            message: "请输入正确的11位手机号",
            trigger: "blur",
          },
        ],
        email: [
          {
            pattern: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
            message: "请输入正确的邮箱地址",
            trigger: "blur",
          },
        ],
        state: [{ required: true, message: "请选择状态", trigger: "change" }],
      },
    };
  },
  mounted() {
    this.getDepartments();
    this.getTableData();
  },
  methods: {
    emptyForm() {
      return {
        id: null,
        realName: "",
        tel: "",
        email: "",
        deptId: null,
        type: 1,
        state: 0,
        inputTime: "",
        loginId: null,
      };
    },
    async getDepartments() {
      try {
        const { data } = await this.$http.get("/system/department/list");
        if (data.success) this.departments = data.data || [];
      } catch (e) {
        this.showError(e, "部门列表加载失败");
      }
    },
    departmentName(id) {
      const item = this.departments.find((v) => String(v.id) === String(id));
      return item ? item.name : "-";
    },
    typeName(v) {
      const item = this.typeOptions.find((i) => i.value === v);
      return item ? item.label : "-";
    },
    stateName(v) {
      const item = this.stateOptions.find((i) => i.value === v);
      return item ? item.label : "-";
    },
    async getTableData() {
      this.loading = true;
      try {
        const { data } = await this.$http.post(
          "/system/employee/pagelist",
          this.query
        );
        if (!data.success) throw new Error(data.message);
        this.tableData = data.data.rows;
        this.total = Number(data.data.total);
      } catch (e) {
        this.showError(e, "数据加载失败");
      } finally {
        this.loading = false;
      }
    },
    search() {
      this.query.page = 1;
      this.getTableData();
    },
    resetSearch() {
      this.query.keyword = "";
      this.search();
    },
    changePage(v) {
      this.query.page = v;
      this.getTableData();
    },
    changeSize(v) {
      this.query.rows = v;
      this.query.page = 1;
      this.getTableData();
    },
    add() {
      this.form = this.emptyForm();
      this.dialogVisible = true;
      this.clearValidate();
    },
    edit(row) {
      this.form = Object.assign(this.emptyForm(), row);
      this.dialogVisible = true;
      this.clearValidate();
    },
    clearValidate() {
      this.$nextTick(() => this.$refs.form && this.$refs.form.clearValidate());
    },
    save() {
      this.$refs.form.validate(async (valid) => {
        if (!valid) return;
        this.saving = true;
        try {
          const { data } = await this.$http.post(
            "/system/employee/save",
            this.form
          );
          if (!data.success) throw new Error(data.message);
          this.$message.success("保存成功");
          this.dialogVisible = false;
          this.getTableData();
        } catch (e) {
          this.showError(e, "保存失败");
        } finally {
          this.saving = false;
        }
      });
    },
    async del(row) {
      try {
        await this.$confirm(
          `确定删除“${row.realName || row.id}”吗？`,
          "删除确认",
          { type: "warning" }
        );
        const { data } = await this.$http.delete(`/system/employee/${row.id}`);
        if (!data.success) throw new Error(data.message);
        this.$message.success("删除成功");
        if (this.tableData.length === 1 && this.query.page > 1)
          this.query.page--;
        this.getTableData();
      } catch (e) {
        if (e !== "cancel" && e !== "close") this.showError(e, "删除失败");
      }
    },
    showError(e, t) {
      this.$message.error(
        (e.response && e.response.data && e.response.data.message) ||
          e.message ||
          t
      );
    },
  },
};
</script>
<style scoped>
.pager {
  margin-top: 20px;
  text-align: right;
}
.operation-buttons {
  white-space: nowrap;
}
</style>
