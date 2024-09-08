

export const API_URL = "http://localhost:8080/api/";



export class HttpConfig {
    static postHeader = (obj) => {
        const header = {
            method: 'POST',
            headers : {
                'Content-Type': 'application/json'
            }
        };
        header.body = JSON.stringify(obj);
        return header;
    }
}






