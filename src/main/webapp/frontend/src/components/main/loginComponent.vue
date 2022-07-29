<template>

  <div id="loginComponent">

    <label class="headline">
      <input type="username" class="form-control" placeholer="Email"/>
    </label>

      <label class="headline">
      <input type="password" class="form-control" placeholer="Password"/>
    </label>

      <button class="btn btn-secondary loginbutton" @click="submitLoginData()"/>
      <div class="result window" v-if="hasLoginFailed()">
        Login failed
    </div>
  </div>
</template>

<script>
export default {
  name: "loginComponent",
        obj:{failed:false, username:"", password:""},

  methods: {
    submitLoginData() { // Hier muessen die input Variablen rein
      this.username = "Admin";
      this.password = "secret";
      const loginData = ["Admin", "secret"];
      const response = this.$store.dispatch('getJSONWebToken', loginData); // Daten von Input Felder auslesen und in Variablen speichern
      const token = response.data;

      if(response.code != 200 || token === undefined) {
        this.failed = true;
      }
      else {
      this.failed = false;
      }
},
    hasLoginFailed() {
      return this.failed;
    }

  }

}
</script>

<style scoped>
.headline {
  background: #434fe8;
  padding: 40px;
  display: flex;
  position: center;

}

.form-control {
  position: center;
}

.loginbutton {
  margin-left: 20px;
}

</style>