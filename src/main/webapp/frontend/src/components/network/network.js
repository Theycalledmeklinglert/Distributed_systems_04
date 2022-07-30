// @@@START:"Network.js"
import axios from 'axios';

class NetworkService {

    httpClient = axios.create({
        headers: {
            "Accept": "application/json",
        }
    });

    sendLoginRequest(loginData) {
        const headers = {headers:{"Accept": "application/json", "Authorization": loginData}};

        return this.httpClient.get("http://localhost:8081/exam03/api/users/me", headers);
    }

}


export default new NetworkService();
