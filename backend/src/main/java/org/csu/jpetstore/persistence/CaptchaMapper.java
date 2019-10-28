package org.csu.jpetstore.persistence;

import org.csu.jpetstore.domain.Captcha;
import org.springframework.stereotype.Repository;

@Repository
public interface CaptchaMapper {
    Captcha queryByCaptcha(String captcha);

    Captcha queryByToken(String token);

    void deleteByToken(String token);

    void update(Captcha captcha);

    void save(Captcha captcha);
}
