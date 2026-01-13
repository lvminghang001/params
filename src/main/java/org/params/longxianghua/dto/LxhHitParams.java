package org.params.longxianghua.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LxhHitParams extends LxhBaseParams{
    private String phoneMd5;

    public String toString() {
        return "LxhHitParams {phoneMd5=" + phoneMd5 + super.toString()+"}";
    }
}
