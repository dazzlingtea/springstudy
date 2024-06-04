

//디바운싱 함수 정의
export function debounce(callback, wait) {
    let timeout;

    return (...args) => {
        clearTimeout(timeout);
        timeout = setTimeout(() => callback(...args), wait);
    };
}
// Throttle 쓰로틀
// export function debounce(callback, wait) {
//     let timeout = null;
//     let lastArgs = null;
//
//     return (...args) => {
//         if (!timeout) {
//             timeout = setTimeout(() => {
//                 timeout = null;
//                 if (lastArgs) {
//                     callback(...lastArgs);
//                     lastArgs = null;
//                 }
//             }, wait);
//             callback(...args);
//         } else {
//             lastArgs = args;
//         }
//     };
// }
