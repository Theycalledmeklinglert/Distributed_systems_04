<template>
  <div id="app">
    <div class="wrapper">
        <loginComponent/>
      </div>
  </div>
</template>

<script>

import loginComponent from "@/components/main/loginComponent";

export default {
  name: 'App',
  components: {
    loginComponent
  }
}
</script>

<style>
#app {
  margin-top: 250px;
}

.wrapper {
  left: 50%;
}

</style>
