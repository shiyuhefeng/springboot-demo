<template>
  <div id="news">
    <div class="table">
      <!-- 功能区 -->
      <div class="button">
        <el-button type="primary" @click="addNews()" size="small"
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
        <el-table-column prop="title" label="标题" />
        <el-table-column prop="author" label="作者" />
        <el-table-column prop="time" label="时间" />
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button type="text" size="small" @click="handleDetail(scope.row)"
              >详情</el-button
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
          v-model:currentPage="currentPage"
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
          width="50%"
          :before-close="handleClose"
        >
          <el-form
            :model="form"
            label-width="80px"
            :label-position="labelPosition"
          >
            <el-form-item label="标题">
              <el-input style="width: 80%" v-model="form.title"></el-input>
            </el-form-item>
            <div id="div1"></div>
          </el-form>

          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisible = false">取 消</el-button>
              <el-button
                type="primary"
                @click="(dialogVisible = false), saveNews()"
                >确 定</el-button
              >
            </span>
          </template>
        </el-dialog>

        <el-dialog
          v-model="dialogVis"
          title="详情"
          width="50%"
          :before-close="handleClose"
        >
          <el-card>
            <div v-html="detail.content" style="min-height: 100px"></div>
          </el-card>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script>
import E from 'wangeditor'

import request from "../utils/request.js"

let editor;
export default {
  name: 'News',
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
      dialogVis: false,
      totalPage: 10,
      tableData: [],
      user: {},
      detail: {},
      filesUploadUrl: 'http://121.89.200.109:9090/files/editor/upload'
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/news', {
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
    addNews() {
      this.dialogVisible = true
      this.form = {}
      if (!editor) {
        this.$nextTick(() => {
          // 关联弹窗里面的div  new一个editor对象
          editor = new E('#div1')
          // 配置 server 接口地址
          editor.config.uploadImgServer = this.filesUploadUrl
          // 设置上传名称
          editor.config.uploadFileName = 'file'
          editor.create()
        })
      } else {
        editor.txt.html('')
      }
    },
    saveNews() {
      // 获取编辑器里面的值  赋值到实体中
      this.form.content = editor.txt.html()
      if (this.form.id) {
        // 更新
        request.put('/news', this.form).then(res => {
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
        // 新增
        let userStr = sessionStorage.getItem('user') || '{}'
        let user = JSON.parse(userStr)
        this.form.author = user.nickName
        request.post('/news', this.form).then(res => {
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
      this.dialogVisible = true

      if (!editor) {
        this.$nextTick(() => {
          // 关联弹窗里面的div  new一个editor对象
          editor = new E('#div1')
          // 配置 server 接口地址
          editor.config.uploadImgServer = this.filesUploadUrl
          // 设置上传名称
          editor.config.uploadFileName = 'file'
          editor.create()
          editor.txt.html(row.content)
        })
      } else {
        editor.txt.html(row.content)
      }
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
      request.delete('/news/' + row).then(res => {
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
    },
    handleDetail(row) {
      this.detail = row
      this.dialogVis = true
    }
  }
}
</script>

<style scoped>
#news {
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
