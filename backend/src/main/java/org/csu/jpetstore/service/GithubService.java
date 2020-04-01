package org.csu.jpetstore.service;

import org.csu.jpetstore.domain.Github;
import org.csu.jpetstore.persistence.GithubMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GithubService {

    @Autowired
    GithubMapper githubMapper;

    public Github getGithub(String githubId) {
        return githubMapper.getGithubByGithubId(githubId);
    }

    public void insert(Github github) {
        githubMapper.insertGithub(github);
    }

    public void update(Github github) {
        githubMapper.updateGithub(github);
    }
}
