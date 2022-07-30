import Vue from 'vue'
import Vuex from 'vuex'
import network from "@/components/network/network";

Vue.use(Vuex)

export const store = new Vuex.Store({
    state: {
        token: {},
    },
    mutations: {
    SET_TOKEN (state, token) {
        state.token = token;
    },
},
actions: {
    async getJSONWebToken(context, loginData) {
        const username = loginData[0];
        const password = loginData[1];
        console.log(username, password);
        const authHeader = 'Basic '+ btoa(username +':'+ password);
        console.log(authHeader);
        const response = await network.sendLoginRequest(authHeader);
        context.commit('SET_TOKEN', response.data)
        console.log("In store.js " + response.data);
        return response;

    },
},
getters: {
    wasLoginSuccesfull(state) {
        return state.token != undefined;
    },

}
})



