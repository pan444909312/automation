package com.miller.service.framework.util;

import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Java 操作 Git 的客户端工具类
 *
 * @author Miller Shan
 * @version 1.0
 * @since 2024/5/15 17:54:43
 */
public class JGitUtils {
    /**
     * 获取 Git 仓库的用户名
     *
     * @return 用户名
     */
    public static String getGitName() {
        Repository repository = getLocalRepository();
        String name = repository.getConfig().getString(ConfigConstants.CONFIG_USER_SECTION, null, ConfigConstants.CONFIG_KEY_NAME);
        if (name != null) {
            return name;
        } else {
            throw new RuntimeException("Git用户名未配置或获取失败，返回了null。");
        }
    }

    /**
     * 获取 Git 仓库的邮箱
     *
     * @return 邮箱地址
     */
    public static String getGitEmail() {
        Repository repository = getLocalRepository();
        String email = repository.getConfig().getString(ConfigConstants.CONFIG_USER_SECTION, null, ConfigConstants.CONFIG_KEY_EMAIL);
        if (email != null) {
            return email;
        } else {
            throw new RuntimeException("Git邮箱未配置或获取失败，返回了null。");
        }
    }

    /**
     * 获取项目路径下的 Git 仓库对象
     *
     * @return Repository
     */
    private static Repository getLocalRepository() {
        // 假设你的项目是一个Git仓库，并且你的工作目录是仓库的根目录
        // 如果不是，你需要提供正确的Git仓库路径
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        String projectRootPath = new File(System.getProperty("user.dir")).getParent();
        File gitPath = new File(projectRootPath + File.separator + ".git");
        repositoryBuilder.setGitDir(gitPath) // 设置.git目录
                .readEnvironment() // 从环境变量中读取配置
                .findGitDir() // 寻找.git目录
                .setMustExist(true);// setMustExist(true) 确保了只有在找到存在的Git仓库时才会构建Repository对象
        Repository repository = null;
        try {
            // 如果.git目录不存在，build()方法将抛出一个异常。
            repository = repositoryBuilder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return repository;
    }
}
