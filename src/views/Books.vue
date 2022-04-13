<template>
  <div id="books">
    <div class="table">
      <!-- 功能区 -->
      <div class="button">
        <el-button type="primary" @click="addbook()" size="small"
          >新增</el-button
        >
        <el-popconfirm title="确定删除吗" @confirm="deleteBatch">
          <template #reference>
            <el-button type="danger" size="small" v-if="user.role === 1"
              >批量删除</el-button
            >
          </template>
        </el-popconfirm>
        <!-- <el-button type="primary" size="small">导入</el-button> -->
        <!-- <el-button type="primary" size="small">导出</el-button> -->
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
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" />
        <el-table-column prop="id" label="ID" sortable />
        <el-table-column prop="bookName" label="书名" />
        <el-table-column prop="bookPrice" label="价格" />
        <el-table-column prop="bookAuthor" label="作者" />
        <el-table-column prop="createTime" label="出版时间" />
        <el-table-column prop="cover" label="封面">
          <template #default="scope">
            <el-image
              style="width: 100px; height: 100px"
              :src="scope.row.cover"
              :preview-src-list="[scope.row.cover]"
              :initial-index="1"
            >
            </el-image>
          </template> </el-table-column
        >>
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
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
            <el-form-item label="书名">
              <el-input style="width: 80%" v-model="form.bookName"></el-input>
            </el-form-item>
            <el-form-item label="价格">
              <el-input style="width: 80%" v-model="form.bookPrice"></el-input>
            </el-form-item>
            <el-form-item label="作者">
              <el-input style="width: 80%" v-model="form.bookAuthor"></el-input>
            </el-form-item>
            <el-form-item label="出版时间">
              <el-date-picker
                v-model="form.createTime"
                value-format="YYYY-MM-DD"
                type="data"
                style="width: 80%"
                clearable
              ></el-date-picker>
            </el-form-item>
            <el-form-item label="封面" style="width: 80px">
              <el-upload
                style="width: 300px"
                :action="filesUploadUrl"
                :on-success="fileUploadSuccess"
                :limit="1"
                ref="upload"
              >
                <el-button type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-form>

          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button
                type="primary"
                @click="(dialogVisible = false), savebook()"
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
  name: 'Books',
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
      tableData: [],
      filesUploadUrl: "http://localhost:9090/files/upload",
      user: JSON.parse(sessionStorage.getItem('user')) || {},
      ids: []
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleSelectionChange(val) {
      this.ids = val.map(v => v.id)
    },
    deleteBatch() {
      if (!this.ids.length) {
        this.$message.warning('请选择数据!')
        return
      }
      request.post('/books/deleteBatch', this.ids).then(res => {
        if (res.code === '0') {
          this.$message.success('批量删除成功')
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
      // console.log('删除数据' + this.ids)

    },
    load() {
      request.get('/books', {
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
    addbook() {
      this.dialogVisible = true
      this.form = {}
      // 清除历史文件列表
      this.$nextTick(() => {
        if (this.$refs['upload']) {
          this.$refs['upload'].clearFiles()
        }
      })
    },
    savebook() {
      if (this.form.id) {
        request.put('/books', this.form).then(res => {
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
        request.post('/books', this.form).then(res => {
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
      request.delete('/books/' + row).then(res => {
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
    },
    fileUploadSuccess(response) {
      console.log(response)
      this.form.cover = response.data
    }
  }
}
</script>

<style scoped>
#books {
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
