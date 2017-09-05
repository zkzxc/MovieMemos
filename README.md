# MovieMemos

## 电影手账

该项目为根据本人爱好完全独立开发的APP。该APP利用豆瓣API获取豆瓣网的影视信息，实现搜索与查看影视作品信息的功能，继而利用所得信息让用户记录、评价和保存已看和想看的影视作品。

本APP参照了Google官方的开源项目Android Architecture中的todo-databinding分支，整体使用了MVP + DataBinding的设计架构。Model负责获取与存储数据，View负责界面展示，Presenter负责Model与View衔接和相关业务逻辑，实现了数据维护与界面展示的分离。利用Google自己的DataBinding框架用来更方便得在布局文件上展示数据，和在java代码中方便得获取布局控件。

所有影视资源数据暂时均通过豆瓣API从豆瓣网获取。网络框架使用的是retrofit2，图片加载使用的是Glide。

## 截图

![电影展示页面](http://7xjamj.com1.z0.glb.clouddn.com/app/moviememos/ss/00.png)

![首页侧滑](http://7xjamj.com1.z0.glb.clouddn.com/app/moviememos/ss/01.png)

![首页已看列表展示](http://7xjamj.com1.z0.glb.clouddn.com/app/moviememos/ss/02.png)

![搜索列表展示](http://7xjamj.com1.z0.glb.clouddn.com/app/moviememos/ss/03.png)

![记录与评分页面1](http://7xjamj.com1.z0.glb.clouddn.com/app/moviememos/ss/04.png)

![记录与评分页面2](http://7xjamj.com1.z0.glb.clouddn.com/app/moviememos/ss/05.png)
