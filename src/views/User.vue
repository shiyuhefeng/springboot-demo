<template>
  <div id="home">
    <div class="table">
      <!-- 功能区 -->
      <div class="button">
        <el-button type="primary" @click="addUser()" size="small"
          >新增</el-button
        >
        <el-button type="primary" size="small">导入</el-button>
        <el-button type="primary" size="small">导出</el-button>
      </div>
      <!-- 搜索区 -->
      <div class="search">
        <el-input
          v-model="search"
          placeholder="请输入关键字"
          clearable
          style="width: 30%"
        />
        <el-button
          type="primary"
          size="small"
          style="margin-left: 10px"
          @click="load()"
          >搜索</el-button
        >
      </div>
      <!-- 表格 -->
      <el-table
        :data="tableData"
        stripe
        style="width: 100%"
        border="true"
        size="mini"
      >
        <el-table-column prop="id" label="ID" sortable />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="nickName" label="昵称" />
        <el-table-column prop="age" label="年龄" />
        <el-table-column prop="sex" label="性别" />
        <el-table-column prop="address" label="地址" />
        <el-table-column label="角色">
          <template #default="scope">
            <span v-if="scope.row.role === 1">管理员</span>
            <span v-if="scope.row.role === 0">普通用户</span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button plain size="small" @click="showBooks(scope.row.bookList)"
              >查看图书列表</el-button
            >
            <el-button type="text" size="small" @click="handleEdit(scope.row)"
              >编辑</el-button
            >
            <el-popconfirm
              confirm-button-text="确认"
              cancel-button-text="不"
              @confirm="handleDelete(scope.row.id)"
              :icon="InfoFilled"
              icon-color="red"
              title="确认删除吗"
            >
              <template #reference>
                <el-button type="text" size="small">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          :currentPage="currentPage"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="totalPage"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        >
        </el-pagination>
      </div>

      <!-- 弹窗 -->
      <el-dialog title="用户拥有的图书列表" v-model="bookView" width="30%">
        <el-table :data="bookList" stripe border>
          <el-table-column prop="id" label="ID"></el-table-column>
          <el-table-column prop="bookName" label="名称"></el-table-column>
          <el-table-column prop="bookPrice" label="价格"></el-table-column>
        </el-table>
      </el-dialog>

      <div class="dialog">
        <el-dialog
          v-model="dialogVisible"
          title="提示"
          width="30%"
          :before-close="handleClose"
        >
          <el-form
            :model="form"
            label-width="80px"
            :label-position="labelPosition"
          >
            <el-form-item label="用户名">
              <el-input v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="昵称">
              <el-input v-model="form.nickName"></el-input>
            </el-form-item>
            <el-form-item label="年龄">
              <el-input v-model="form.age"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <div>
                <el-radio v-model="form.sex" label="男">男</el-radio>
                <el-radio v-model="form.sex" label="女">女</el-radio>
              </div>
            </el-form-item>
            <el-form-item label="地址">
              <el-input v-model="form.address"></el-input>
            </el-form-item>
          </el-form>

          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button
                type="primary"
                @click="(dialogVisible = false), saveUser()"
                >确 定</el-button
              >
            </span>
          </template>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import request from "../utils/request.js"

export default {
  name: 'User',
  components: {
  },
  data() {
    return {
      search: '',
      form: {},
      currentPage: 1,
      pageSize: 10,
      labelPosition: 'right',
      dialogVisible: false,
      totalPage: 10,
      tableData: [

      ],
      bookList: [],
      bookView: false
    }
  },
  created() {
    this.load()
  },
  methods: {
    showBooks(books) {
      this.bookList = books
      this.bookView = true
    },
    load() {
      request.get('/user', {
        params: {
          pageNo: this.currentPage,
          pageSize: this.pageSize,
          search: this.search
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.totalPage = res.data.total
      })
    },
    addUser() {
      this.dialogVisible = true
      this.form = {}
    },
    saveUser() {
      if (this.form.id) {
        request.put('/user', this.form).then(res => {
          console.log(res)
          if (res.code == '0') {
            this.$message({
              type: 'success',
              message: '更新成功'
            })
          } else {
            this.$message({
              type: 'error',
              message: '更新失败'
            })
          }
          this.load() // 刷新表格数据
          this.dialogVisible = false // 关闭弹窗
        })
      } else {
        request.post('/user', this.form).then(res => {
          console.log(res)
          if (res.code == '0') {
            this.$message({
              type: 'success',
              message: '新增成功'
            })
          } else {
            this.$message({
              type: 'error',
              message: '新增失败'
            })
          }
          this.load() // 刷新表格数据
          this.dialogVisible = false // 关闭弹窗
        })
      }
    },
    handleEdit(row) {
      // 深拷贝  独立的对象  跟表格对象独立开
      this.form = JSON.parse(JSON.stringify(row))
      console.log(this.form)
      this.dialogVisible = true
    },
    // 改变当前每页的个数触发
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    // 改变当前页码触发
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum
      this.load()
    },
    handleDelete(row) {
      console.log(row)
      request.delete('/user/' + row).then(res => {
        if (res.code == '0') {
          this.$message({
            type: 'success',
            message: '删除成功'
          })
        } else {
          this.$message({
            type: 'error',
            message: '删除失败'
          })
        }
      })
      this.load() // 更新数据
    }
  }

}
</script>

<style scoped>
#home {
  flex: 1;
}
.table {
  padding: 5px;
}
.button {
  margin: 10px 0;
}
.search {
  margin-bottom: 10px;
}
.pagination {
  margin: 10px 0;
}
</style>
