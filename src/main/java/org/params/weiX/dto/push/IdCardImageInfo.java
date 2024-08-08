package org.params.weiX.dto.push;

import lombok.Data;

@Data
public class IdCardImageInfo {
    /**
     * 正面照
     */
    private String idFrontImage;

    /**
     * 背面照
     */
    private String idBackImage;

    /**
     * 活体照
     */
    private String livingBodyImage;
}
