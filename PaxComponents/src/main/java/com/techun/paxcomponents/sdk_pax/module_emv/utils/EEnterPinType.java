package com.techun.paxcomponents.sdk_pax.module_emv.utils;

public enum EEnterPinType {
    ONLINE_PIN, // 联机pin
    OFFLINE_PLAIN_PIN, // 脱机明文pin
    OFFLINE_CIPHER_PIN, // 脱机密文pin
    OFFLINE_PCI_MODE, //JEMV PCI MODE, no callback for offline pin
}
