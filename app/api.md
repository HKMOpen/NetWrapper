# get String

GET http://www.qxinli.com/Application/about/androidAbout.html



响应: 一段html代码

# post String

POST http://www.qxinli.com:9001/api/article/getArticleCommentList/v1.json HTTP/1.1

​	pageSize=30&session_id=&articleId=1738&pageIndex=1&

响应: 一个jsonobj

# get json

 GET http://www.qxinli.com:9001/api/version/latestVersion/v1.json 

响应:

{"data":{"id":25,"platform":1,"version":"2.0.0","description":"1.咨询功能隆重推出，众多咨询套餐，360°贴心咨询服务\n2.咨询师在线操作，可给用户发布多种咨询任务\n3.购买咨询产品，还可以分享二维码，邀请家人来共享\n4.新增邀请码，邀请好友一起来“玩”心理\n5.修复Bug，优化性能，使用更加流畅","status":1,"publishTime":1474162283000,"publishTimeFormat":"2016-09-18 09:31","url":"http://static.qxinli.com/android_apk:2016091809305932.apk","forceUpdate":0,"code":23,"md5":"064E3D51525743E9CB25A91DC62058CA"},"code":0}

# post json

POST http://www.qxinli.com:9001/api/article/getArticleCommentList/v1.json HTTP/1.1

​	pageSize=30&session_id=&articleId=1738&pageIndex=1&

响应:

{"data":[{"uid":54140,"createTime":1474124040,"nickName":"隨 訫","articleId":1738,"avatar":"http://www.qxinli.com/Uploads/Picture/2015-11-27/56583b0d4093f.png","showRole":1,"content":"回复 @木兰殇 ：这个问题我也在思考"},{"uid":55722,"createTime":1473805862,"nickName":"木兰殇","articleId":1738,"avatar":"http://www.qxinli.com/Uploads/Picture/2015-11-27/56583b0d4093f.png","showRole":1,"content":"回复 @圆滚滚 ：人往往做起来会很难"},{"uid":55722,"createTime":1473805815,"nickName":"木兰殇","articleId":1738,"avatar":"http://www.qxinli.com/Uploads/Picture/2015-11-27/56583b0d4093f.png","showRole":1,"content":"嗯嗯，我也是这样想的"},{"uid":511,"createTime":1473776866,"nickName":"圆滚滚","articleId":1738,"avatar":"http://7xnwnf.com2.z0.glb.qiniucdn.com/Uploads_Avatar_511_565fd7cc53c1e.jpg?imageMogr2/crop/!100x100a0a0","showRole":1,"content":"虽然知道改变认知很重要，但是怎么改变"}],"code":0}



# get 标准 json

 GET http://www.qxinli.com:9001/api/version/latestVersion/v1.json 

响应:

{"data":{"id":25,"platform":1,"version":"2.0.0","description":"1.咨询功能隆重推出，众多咨询套餐，360°贴心咨询服务\n2.咨询师在线操作，可给用户发布多种咨询任务\n3.购买咨询产品，还可以分享二维码，邀请家人来共享\n4.新增邀请码，邀请好友一起来“玩”心理\n5.修复Bug，优化性能，使用更加流畅","status":1,"publishTime":1474162283000,"publishTimeFormat":"2016-09-18 09:31","url":"http://static.qxinli.com/android_apk:2016091809305932.apk","forceUpdate":0,"code":23,"md5":"064E3D51525743E9CB25A91DC62058CA"},"code":0}

​	

# post 标准json



## jsonarray

POST http://www.qxinli.com:9001/api/article/getArticleCommentList/v1.json HTTP/1.1

​	pageSize=30&session_id=&articleId=1738&pageIndex=1&

响应:

{"data":[{"uid":54140,"createTime":1474124040,"nickName":"隨 訫","articleId":1738,"avatar":"http://www.qxinli.com/Uploads/Picture/2015-11-27/56583b0d4093f.png","showRole":1,"content":"回复 @木兰殇 ：这个问题我也在思考"},{"uid":55722,"createTime":1473805862,"nickName":"木兰殇","articleId":1738,"avatar":"http://www.qxinli.com/Uploads/Picture/2015-11-27/56583b0d4093f.png","showRole":1,"content":"回复 @圆滚滚 ：人往往做起来会很难"},{"uid":55722,"createTime":1473805815,"nickName":"木兰殇","articleId":1738,"avatar":"http://www.qxinli.com/Uploads/Picture/2015-11-27/56583b0d4093f.png","showRole":1,"content":"嗯嗯，我也是这样想的"},{"uid":511,"createTime":1473776866,"nickName":"圆滚滚","articleId":1738,"avatar":"http://7xnwnf.com2.z0.glb.qiniucdn.com/Uploads_Avatar_511_565fd7cc53c1e.jpg?imageMogr2/crop/!100x100a0a0","showRole":1,"content":"虽然知道改变认知很重要，但是怎么改变"}],"code":0}



## json object

POST http://www.qxinli.com:9001/api/faceSwiping/dailyRanking/v1.json 

pageSize=30&session_id=&time=1474300800162&pageIndex=1&



{"data":{"selfInfo":{},"overAvgNum":0,"avgScore":10000,"selfRanking":0,"ranking":[{"score":10000,"createTime":1474334684000,"praiseCount":0,"words":"Great！你的笑容千金难买哦！","isPraise":0,"id":5413,"user":{"id":49845,"name":"","nickname":"远方恒星","avatar":"http://www.qxinli.com/Uploads/Picture/2015-11-27/56583b0d4093f.png","sex":0,"score":75.0,"showRole":1,"smallWaistcoat":0,"isFollow":0,"mobile":""},"emoticon":"http://7xmzc7.com1.z0.glb.clouddn.com/face:emotion_6_2.png","desc":"，，，，"}],"overRate":"0%"},"code":0}





# 文件下载

http://www.qxinli.com/download/qxinli.apk

# 文件上传

```
http://api.cn.faceplusplus.com/detection/detect
```



```
必须 
api_key    App的Face++ API Key   4d3d1f40e7a841316084b64c0c4575b1
api_secret APP的Face++ API Secret  VQ8bciAjkUl4fiTTvafdvTLnBNGlSS-3
url 或 img[POST]    待检测图片的URL 或者 通过POST方法上传的二进制数据，原始图片大小需要小于1M
```





http://192.168.1.100:8080/gm/file/q_uploadAndroidApk.do 



Content-Disposition: form-data; name="uploadFile"; filename="app-androidmarket-debug.apk"
Content-Type: application/octet-stream



