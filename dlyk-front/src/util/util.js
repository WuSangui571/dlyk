import {ElMessage} from "element-plus";

export function messageTip(msg,type){
    // messageTip 函数
    ElMessage({
        // true 代表提示信息可被关闭
        showClose: true,
        // true 代表显示居中
        center: true,
        // 设置提示信息的消失时间（单位：ms）
        duration: 3000,
        // 示例 msg 的值：'登录成功，欢迎回来！',
        message: msg,
        // 示例 type 的值：可选 "error"|"success"|"warning"|"primary"
        type: type,
    })
}