package com.wangguangwu.session;

import com.alibaba.fastjson.JSON;
import lombok.*;

/**
 * @author wangguangwu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private String userId;

    private String username;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
