<template>

  <div class="loginComponent">
  Login
    <label class="inputFields">
      <input type="username" id="username" class="form-control" placeholder="Email"/>
    </label>

      <label class="inputFields">
      <input type="password" id="password" class="form-control" placeholder="Password"/>
    </label>

      <button class="btn btn-secondary loginbutton" @click="submitLoginData()">
        Login
      </button>
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
        doc: "",
      failed: "",
      error: ""
    }
    },

  methods: {
    async submitLoginData() { // Hier muessen die input Variablen rein
      this.username = document.getElementById("username").value;
      this.password = document.getElementById("password").value;
      const loginData = [this.username, this.password];
      try {
        const response = await this.$store.dispatch('getJSONWebToken', loginData);
        console.log(response.status);
        console.log("Successful Login!");
        this.failed = "";
      }
      catch(e) {
        console.log(e);
        this.failed = "Login Failed: " + e;
      }

      },


    }
}
</script>

<style scoped>

.loginComponent {
  margin-left: 45%;
}

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
  width: 70px;
  height: 30px;

}

.resultWindow {
  padding: 40px;
  display: flex;
  position: center;
}

</style>