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
      /><el-table-column prop="sn" label="部门编号" /><el-table-column
        prop="name"
        label="部门名称"
      /><el-table-column label="上级部门"
        ><template slot-scope="scope">{{
          departmentName(scope.row.parentId)
        }}</template></el-table-column
      ><el-table-column label="部门经理"
        ><template slot-scope="scope">{{
          employeeName(scope.row.managerId)
        }}</template></el-table-column
      ><el-table-column prop="dirPath" label="层级路径" /><el-table-column
        label="状态"
        width="90"
        ><template slot-scope="scope"
          ><el-tag
            :type="scope.row.state === 0 ? 'success' : 'warning'"
            size="small"
            >{{ scope.row.state === 0 ? "正常" : "禁用" }}</el-tag
          ></template
        ></el-table-column
      ><el-table-column label="操作" width="190" fixed="right" align="center"
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
      :title="form.id ? '编辑部门' : '新增部门'"
      :visible.sync="dialogVisible"
      width="560px"
      :close-on-click-modal="false"
      ><el-form ref="form" :model="form" :rules="rules" label-width="100px"
        ><el-form-item label="部门编号" prop="sn"
          ><el-input v-model.trim="form.sn" maxlength="50" /></el-form-item
        ><el-form-item label="部门名称" prop="name"
          ><el-input v-model.trim="form.name" maxlength="50" /></el-form-item
        ><el-form-item label="上级部门" prop="parentId"
          ><el-select
            v-model="form.parentId"
            clearable
            filterable
            style="width: 100%"
            ><el-option
              v-for="item in departments"
              :key="item.id"
              :label="item.name"
              :value="item.id" /></el-select></el-form-item
        ><el-form-item label="部门经理" prop="managerId"
          ><el-select
            v-model="form.managerId"
            clearable
            filterable
            style="width: 100%"
            ><el-option
              v-for="item in employees"
              :key="item.id"
              :label="item.realName"
              :value="item.id" /></el-select></el-form-item
        ><el-form-item label="层级路径" prop="dirPath"
          ><el-input
            v-model.trim="form.dirPath"
            maxlength="255" /></el-form-item
        ><el-form-item label="租户ID" prop="tenantId"
          ><el-input-number
            v-model="form.tenantId"
            :min="0"
            style="width: 100%" /></el-form-item
        ><el-form-item label="状态" prop="state"
          ><el-select v-model="form.state" style="width: 100%"
            ><el-option label="正常" :value="0" /><el-option
              label="禁用"
              :value="1" /></el-select></el-form-item></el-form
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
      employees: [],
      total: 0,
      loading: false,
      saving: false,
      dialogVisible: false,
      form: {},
      rules: {
        sn: [{ required: true, message: "请输入部门编号", trigger: "blur" }],
        name: [{ required: true, message: "请输入部门名称", trigger: "blur" }],
        state: [{ required: true, message: "请选择状态", trigger: "change" }],
      },
    };
  },
  mounted() {
    this.getOptions();
    this.getTableData();
  },
  methods: {
    emptyForm() {
      return {
        id: null,
        sn: "",
        name: "",
        parentId: null,
        managerId: null,
        dirPath: "",
        tenantId: null,
        state: 0,
      };
    },
    async getOptions() {
      try {
        const [deptRes, employeeRes] = await Promise.all([
          this.$http.get("/system/department/list"),
          this.$http.get("/system/employee/list"),
        ]);
        if (deptRes.data.success) this.departments = deptRes.data.data || [];
        if (employeeRes.data.success)
          this.employees = employeeRes.data.data || [];
      } catch (e) {
        this.showError(e, "下拉列表加载失败");
      }
    },
    departmentName(id) {
      const item = this.departments.find((v) => String(v.id) === String(id));
      return item ? item.name : "-";
    },
    employeeName(id) {
      const item = this.employees.find((v) => String(v.id) === String(id));
      return item ? item.realName : "-";
    },
    async getTableData() {
      this.loading = true;
      try {
        const { data } = await this.$http.post(
          "/system/department/pagelist",
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
            "/system/department/save",
            this.form
          );
          if (!data.success) throw new Error(data.message);
          this.$message.success("保存成功");
          this.dialogVisible = false;
          this.getOptions();
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
        await this.$confirm(`确定删除“${row.name}”吗？`, "删除确认", {
          type: "warning",
        });
        const { data } = await this.$http.delete(
          `/system/department/${row.id}`
        );
        if (!data.success) throw new Error(data.message);
        this.$message.success("删除成功");
        if (this.tableData.length === 1 && this.query.page > 1)
          this.query.page--;
        this.getOptions();
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
