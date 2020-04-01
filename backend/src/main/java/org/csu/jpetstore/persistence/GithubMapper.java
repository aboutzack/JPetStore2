package org.csu.jpetstore.persistence;

import org.csu.jpetstore.domain.Github;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubMapper {
    Github getGithubByGithubId(String githubId);

    void insertGithub(Github github);

    void updateGithub(Github github);
}
