# pubg-analysiss
绝地求生数据分析

1.部署ES 版本要求:5.6  
2.执行DB目录下的sql与创建ES索引的json文件  
3.配置application文件的相关配置  
4.启动应用  
5.系统会执行定时任务定时获取mysql中已存玩家的接口数据，并更新到ES  

示例demo:  
/players/add 增加玩家  
/matches/list  获取玩家最近的比赛数据


Todo:  
1.使用消息队列初始化新玩家数据
