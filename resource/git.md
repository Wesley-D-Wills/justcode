# Git 总结

[toc]



```shell
git commit 

# 创建分支、并切换分支
git branch [name] # 创建分支
git checkout [name] # 切换分支
# 上面的两步可以合并为一步
git checkout -b [name]

# 分支与合并 [首先切换到 合并的分支上] ## 重点区分合并和被合并 ##
git merge [被合并分支name]

# 第二种合并分支的方法 【首先切换到 被合并的分支上】
git rebase [合并分支name]
```







