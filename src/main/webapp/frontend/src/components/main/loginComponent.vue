<template>

  <div id="loginComponent">

    <label class="inputFields">
      <input type="username" id="username" class="form-control" placeholer="Email"/>
    </label>

      <label class="inputFields">
      <input type="password" id="password" class="form-control" placeholer="Password"/>
    </label>

      <button class="btn btn-secondary loginbutton" @click="submitLoginData()"/>
      <span class="resultWindow" >
        {{ failed }}
    </span>
  </div>
</template>

<script>
export default {
  name: "loginComponent",
  data() {
    return {
        username: "",
        password: "",
      failed: "",
      error: ""
    }
    },

  methods: {
    async submitLoginData() { // Hier muessen die input Variablen rein
      this.username = document.getElementById("username").value;
      this.password = document.getElementById("password").value;
      console.log(this.username + " " + this.password);
      const loginData = [this.username, this.password];
      try {
        const response = await this.$store.dispatch('getJSONWebToken', loginData);
        console.log(response.status);
        console.log("Successful Login!");
        this.failed = "";
      }
      catch(e) {
        console.log(e);
        this.failed = e;
      }

      },

    }
}
</script>

<style scoped>

.inputFields {
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

.resultWindow {
  padding: 40px;
  display: flex;
  position: center;
}

</style>