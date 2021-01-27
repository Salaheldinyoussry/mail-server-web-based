<template>
  <v-app>
    <!-- <HelloWorld />
    <router-link to="/try">hey </router-link>
    <router-view></router-view> -->
    <v-container v-if="signin & !ok">
      <v-card dark class="card">
        <br />
        <br />

        <v-text-field label="email" v-model="email"></v-text-field>
        <br />
        <v-text-field
          type="password"
          label="password"
          v-model="pass"
        ></v-text-field>
        <br />
        <br />
        <v-btn
          white
          class="btt"
          style="color:black; background-color:white; margin-left:20px"
          @click="signinl()"
        >
          sign in</v-btn
        >

        <v-btn @click="toggle()"> sign up instead</v-btn>
      </v-card>
    </v-container>

    <v-container v-if="!signin & !ok">
      <v-card dark class="card">
        <br />
        <br />
        <v-text-field label=" first name" v-model="fn"></v-text-field>
        <br />
        <v-text-field label="last name" v-model="ln"></v-text-field>

        <v-text-field label="email" v-model="em"></v-text-field>
        <br />
        <v-text-field
          type="password"
          label="password"
          v-model="ps"
        ></v-text-field>
        <br />
        <br />
        <v-btn
          style="color:black; background-color:white; margin-left:20px; margin-bottom:100px"
          class="btt"
          @click="signup3()"
        >
          sign up</v-btn
        >
        <v-btn @click="toggle()" style="margin-bottom:100px">
          sign in instead</v-btn
        >
        <br />
        <br />
      </v-card>
    </v-container>
  </v-app>
</template>

<script>
// import HelloWorld from "./components/HelloWorld";
import axios from "axios";

export default {
  name: "App",
  components: {
    // HelloWorld,
  },
  data: () => ({
    port: "http://localhost:8080",
    userName: "salah",
    id: 0,
    email: "",
    pass: "",
    em: "",
    fn: "",
    ln: "",
    ps: "",
    selectedItem: -1,
    signin: false,
    ok: false,
    links: [
      {
        icon: "DashBoard",
        text: "DashBoard",
        route: "/",
      },
      { icon: "email", text: "My Projects", route: "/email" },
      { icon: "person", text: "table", route: "/table/ahmed/sent" },
    ],
    //
  }),
  methods: {
    getUrl2() {
      return "/folders/" + this.userName;
    },
    toggle() {
      this.signin = !this.signin;
    },
    findLink(value) {
      return value.route;
    },
    getIcon(value) {
      if (value != "f") {
        return "mdi - folder";
      }
      return "mdi - folder";
    },
    signinl() {
      axios
        .get(this.port + "/signIn/" + this.email + "/" + this.pass)
        .then((res) => {
          this.id = res.data;

          if (this.id == -1) {
            alert("wrong email or password");
          } else {
            //window.location.assign("http://localhost:8081/" + this.id);
            window.location.assign("http://localhost:8081/home/" + this.id);
          }
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
    },
    signup3() {
      axios({
        url:
          this.port +
          "/signUP/" +
          this.fn +
          "/" +
          this.ln +
          "/" +
          this.em +
          "/" +
          this.ps,
        method: "GEt",
        // data: x,
      }).then((res) => {
        //this.i = res.data;
        if (res.data == false) {
          alert("user already exist!");
        } else {
          alert("Great!! , sign in to access your sevice");
        }
      });
    },
  },
};
</script>
<style scoped>
.card {
  width: 500px;
  height: 600px;
  margin: auto;
  margin-top: 200px;
  padding: 40px;
}
.btt {
  margin-right: 100px;
}
.link {
  text-decoration: none;
  background-color: grey darken-4;
  color: white;
  text-decoration-style: wavy;
  font-size: large;
}
.compose {
  width: 200px;
  margin-top: 10px;
}
</style>
