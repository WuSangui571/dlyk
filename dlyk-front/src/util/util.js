import {ElMessage, ElMessageBox} from "element-plus";

/**
 * 消息提示
 * @param msg
 * @param type
 */
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

/**
 * 返回存储在 Session Storage 或 Local Storage 中的 token（jwt） 名字
 * @returns {string}
 */
export function getTokenName(){
    return "dlyk_token";
}

/**
 * 从本地存储和会话存储中移除 token。
 * 此函数调用 getTokenName() 来获取 token 的键名，然后删除两种存储（localStorage 和 sessionStorage）中的对应条目。
 *
 * @return {void} 此函数不返回任何值
 */
export function removeToken(){
    window.localStorage.removeItem(getTokenName());
    window.sessionStorage.removeItem(getTokenName());
}

export function messageConfirm(msg){
    ElMessageBox.confirm(
        msg,
        'Warning',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(() => { // 若点击"确定"按钮，走这个
            // 跳到登录页
            window.location.href = ("/");
        })
        // 若点击"取消"按钮，走这个
        .catch(() => {
            messageTip("111已取消去登录！","warning")
        })
}