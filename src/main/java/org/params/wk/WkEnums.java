package org.params.wk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum WkEnums {
    WKCONFIG_PRO("",
            "" ,
            ""),
    WKCONFIG_TEST("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxK6fwC/RohZblHThfWVdwTWE7sk+smjJUD2KDWLVhTiCo20z8DVqLfQxCcatA60FQL6GoKcZC14rXi3rT9ufcazLY0/2PkbKk2YpNadv4R69stpm+o32s1TMijjbhD/cNSBq+KQANGT7HeNbJV0J68pZsPQ4hcS8jGwpae2bt/ALCtSinU4squfH1Qa/WkV63gJ08E//elKOS8LHMNlR7tcj5CcKkJrXHSSyC7LdWc6mDzU4lBRY5PDXHHOVieOxUwh4ew+1NITO73StrOVBEzqrgYBeflY6PrhTlZFset86zyBiUHRDFunJEahCjsETDJRtvPTSbMmA84oT0qQ+7QIDAQAB",
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCdKH6ct4uTebTCe1tV3O5PErnncN1aWnSJd0pSokomkwuvGCz25sJJJtzs3zxWAvd9pTux7113Hv2FfORTZZBcx3j44JzDSYZ6fwbXwKd/sItsH4GLwD2b1LgSw/Soep7EXqaqcp4QZ18PFyCnEnWmZOD++gyTHQ6EhEBvhbTpswIDAQAB" ,
            "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ0ofpy3i5N5tMJ7W1Xc7k8Suedw3VpadIl3SlKiSiaTC68YLPbmwkkm3OzfPFYC932lO7HvXXce/YV85FNlkFzHePjgnMNJhnp/BtfAp3+wi2wfgYvAPZvUuBLD9Kh6nsRepqpynhBnXw8XIKcSdaZk4P76DJMdDoSEQG+FtOmzAgMBAAECgYA3b0pB42hja8G7/CE6h0S0NanHnoMl1NRYjPB1d3NqaeHsMD3kFX+gqAmzsAarXD6KXldRK1qvownQJT6wwBmMYu3kI42NJ1Cz0O5EOUShZnpCRIW1yWOSGpeHHvPlLlAPAbiXMPdHEjPYv27OEhey/ScRTYtWuwp5XBMsPuCuwQJBAM4NHS+obu/B4q6RmnGIKzVRXmCvyETTz+WzPInhBknJ7+x/JmdlYIccIVSAMLchwFwDF3wAqceJtiC+Js1RihMCQQDDQTuy4n4LztAefDQRPe5xgeqWaQV14iInoj9Nvn6QJLyu7vjvlhuT0V1I0Q+cxGNtps45vtUp9koGZFjjTRXhAkBnI+9ZHMGzJ+U1JtMdQ2EFhl5snXDs6u2k+Cw0hDwmkXuUcS0jEn7YAr+4UZaRBMcKB84olxhlNkzBZQ19NvSbAkAzm4hV7bt3D4C+rgFtFTty0O3aN4Pz9oMkiqPEvrbhDnS4n/04Birf1RSc7HT98IJ/NvmHFcNtxeUm71r3KSbBAkAsbqE4SdAseRl0ykb+W+bb6FkX9t9YK9tZ+bPRg8IZKhx76VK7LHvNcdOLjbr7m3nGVvULEfDOaGF8b2U0ab5H"),
    ;
    private String thirdPubKey;

    private String pubKey;

    private String prikey;
}
