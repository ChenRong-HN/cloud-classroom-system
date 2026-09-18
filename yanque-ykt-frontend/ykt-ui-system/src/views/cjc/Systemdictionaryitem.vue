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
      /><el-table-column label="所属字典"
        ><template slot-scope="scope">{{
          dictionaryName(scope.row.parentId)
        }}</template></el-table-column
      ><el-table-column prop="name" label="明细名称" /><el-table-column
        prop="requence"
        label="排序号"
      /><el-table-column
        prop="intro"
        label="简介"
        min-width="180"
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
      :title="form.id ? '编辑字典明细' : '新增字典明细'"
      :visible.sync="dialogVisible"
      width="520px"
      :close-on-click-modal="false"
      ><el-form ref="form" :model="form" :rules="rules" label-width="100px"
        ><el-form-item label="所属字典" prop="parentId"
          ><el-select v-model="form.parentId" filterable style="width: 100%"
            ><el-option
              v-for="item in dictionaries"
              :key="item.id"
              :label="item.name"
              :value="item.id" /></el-select></el-form-item
        ><el-form-item label="明细名称" prop="name"
          ><el-input v-model.trim="form.name" maxlength="50" /></el-form-item
        ><el-form-item label="排序号" prop="requence"
          ><el-input-number
            v-model="form.requence"
            :min="0"
            style="width: 100%" /></el-form-item
        ><el-form-item label="简介" prop="intro"
          ><el-input
            v-model.trim="form.intro"
            type="textarea"
            :rows="3"
            maxlength="255"
            show-word-limit /></el-form-item></el-form
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
      dictionaries: [],
      total: 0,
      loading: false,
      saving: false,
      dialogVisible: false,
      form: {},
      rules: {
        parentId: [
          { required: true, message: "请选择所属字典", trigger: "change" },
        ],
        name: [{ required: true, message: "请输入明细名称", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.getDictionaries();
    this.getTableData();
  },
  methods: {
    emptyForm() {
      return { id: null, parentId: null, name: "", requence: 0, intro: "" };
    },
    async getDictionaries() {
      try {
        const { data } = await this.$http.get("/system/systemdictionary/list");
        if (data.success) this.dictionaries = data.data || [];
      } catch (e) {
        this.showError(e, "字典列表加载失败");
      }
    },
    dictionaryName(id) {
      const item = this.dictionaries.find((v) => String(v.id) === String(id));
      return item ? item.name : id || "-";
    },
    async getTableData() {
      this.loading = true;
      try {
        const { data } = await this.$http.post(
          "/system/systemdictionaryitem/pagelist",
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
            "/system/systemdictionaryitem/save",
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
        await this.$confirm(`确定删除“${row.name}”吗？`, "删除确认", {
          type: "warning",
        });
        const { data } = await this.$http.delete(
          `/system/systemdictionaryitem/${row.id}`
        );
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
