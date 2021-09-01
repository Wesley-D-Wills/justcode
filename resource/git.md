# Git 总结

[toc]

## Git 基本用法

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



## Github上传超过100M的文件

```shell
# mac系统使用国内源安装包管理工具homebrew
/bin/zsh -c "$(curl -fsSL https://gitee.com/cunkai/HomebrewCN/raw/master/Homebrew.sh)"

# 然后使用brew进行安装与配置
brew install git-lfs
git lfs install 
git lfs track "*.pdf"  # 切换到想要上传文件的路径目录下面，后缀为上传的文件名
git add .gitattributes
git add filename
git commit -m "mesage"
git push -u origin master

```



