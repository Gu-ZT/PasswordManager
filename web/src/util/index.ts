import {MessageInstance} from "ant-design-vue/es/message/interface";
import CryptoJS from 'crypto-js';

export class Operate {
    public static messageApi: MessageInstance | null = null;

    public static error(msg: string): void {
        if (Operate.messageApi) Operate.messageApi.error(msg);
    }

    public static success(msg: string = '操作成功'): void {
        if (Operate.messageApi) Operate.messageApi.success(msg);
    }

    public static info(msg: string): void {
        if (Operate.messageApi) Operate.messageApi.info(msg);
    }

    public static warning(msg: string): void {
        if (Operate.messageApi) Operate.messageApi.warning(msg);
    }
}

export class AESUtil {
    public static encrypt(data: string, key: string): string {
        let srcs = CryptoJS.enc.Utf8.parse(data);
        let encrypted = CryptoJS.AES.encrypt(srcs, CryptoJS.MD5(key), {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7,
        });
        return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
    };

    public static decrypt(data: string, key: string): string {
        let base64 = CryptoJS.enc.Base64.parse(data);
        let decrypt = CryptoJS.AES.decrypt({ciphertext: base64}, CryptoJS.MD5(key), {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
        return decryptedStr.toString();
    }
}
