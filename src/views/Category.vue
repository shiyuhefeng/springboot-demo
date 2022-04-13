<template>
  <div id="Category">
    <el-table
      :data="tableData"
      style="width: 100%; margin-bottom: 20px"
      row-key="id"
      border
      default-expand-all
      stripe="true"
    >
      <el-table-column prop="name" label="名称" width="300" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="mini" @click="editBtn(scope.row)">编辑</el-button>
          <el-popconfirm
            title="确定删除吗？"
            @confirm="handleDelete(scope.row.id)"
          >
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogFormVisible" title="编辑界面">
      <el-form :model="form">
        <el-form-item label="姓名">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="submitCategory()">提交</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import request from "../utils/request"

export default {
  name: 'Category',
  data() {
    return {
      tableData: [],
      dialogFormVisible: false,
      form: {}
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/category').then(res => {
        console.log(res)
        this.tableData = res.data

        // this.form = res.data[0].children.map(v => v.name)
        // this.form = res.data[0].children
      })
    },
    editBtn(row) {
      console.log(row)
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true

    },
    submitCategory() {
      console.log('点击了发送按钮')
      console.log(this.form[0].children[0].name)
      console.log(this.form)
    },
    handleDelete(id) {
      request.delete('/category/' + id).then(res => {
        if (res.code === '0') {
          this.$message.success('删除成功')
        } else {
          this.$message.eroor(res.msg)
        }
      })
    }
  }


}
</script>

<style scoped>
</style>