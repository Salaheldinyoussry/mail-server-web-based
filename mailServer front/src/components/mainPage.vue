<template>
  <v-app>
    <!-- <HelloWorld />
    <router-link to="/try">hey </router-link>
    <router-view></router-view> -->
    <v-container v-if="signin & !ok">
      <v-card dark class="card">
        <br />
        <br />

        <v-text-field label="email"></v-text-field>
        <br />
        <v-text-field type="password" label="password"></v-text-field>
        <br />
        <br />
        <v-btn
          white
          class="btt"
          style="color:black; background-color:white; margin-left:20px"
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
        <v-text-field label="name"></v-text-field>
        <br />
        <v-text-field label="email"></v-text-field>
        <br />
        <v-text-field type="password" label="password"></v-text-field>
        <br />
        <br />
        <v-btn
          style="color:black; background-color:white; margin-left:20px"
          class="btt"
        >
          sign up</v-btn
        >
        <v-btn @click="toggle()"> sign in instead</v-btn>
      </v-card>
    </v-container>
    <nav v-if="ok">
      <v-toolbar flat app> </v-toolbar>

      <v-navigation-drawer app dark>
        <br />
        <br />

        <h1 style="margin-left:30px; color: white;">
          name dddd
        </h1>
        <br />
        <v-btn
          depressed
          style=" width:300px; padding-right:90px"
          dark
          @click="$router.push(getll('/compose'))"
          >Compose</v-btn
        >
        <!-- <br /> -->
        <!-- <v-btn
          depressed
          dark
          style=" width:300px; padding-right:80px"
          @click="$router.push(getll( '/Folder')/Folder')"
          >New Folder</v-btn
        > -->
        <br />
        <v-btn
          depressed
          dark
          style=" width:300px; padding-right:90px"
          @click="$router.push(getll('/Contacts'))"
          >Contacts</v-btn
        >
        <br />
        <!-- <v-btn
          depressed
          dark
          style=" width:300px; padding-right:80px"
          @click="$router.push('/' + this.userId + '/EmailModification')"
          >Email Modification</v-btn
        >
        <br /> -->
        <v-btn
          depressed
          dark
          style=" width:300px; padding-right:80px"
          @click="$router.push(getUrl2())"
          >Folders Modification</v-btn
        >
        <v-list dense>
          <v-subheader>Folders</v-subheader>
          <v-list-item-group v-model="selectedItem" color="primary">
            <v-list-item v-for="(link, i) in links" :key="i">
              <v-list-item-icon>
                <v-icon>mdi-folder</v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title
                  v-text="link"
                  @click="$router.push(findLink(link))"
                ></v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>

        <br />
        <br />
        <br />
      </v-navigation-drawer>
      <v-container>
        <router-view></router-view>
      </v-container>
    </nav>
  </v-app>
</template>

<script>
// import axios from "axios";
// import HelloWorld from "./components/HelloWorld";
// import axios from "axios";
import axios from "axios";
export default {
  name: "App",
  props: [],

  components: {
    // HelloWorld,
  },
  mounted() {
    /*  var x = window.location.pathname;
    var ind = x.lastIndexOf("8081");
    var ind1 = x.lastIndexOf("/");

    x = x.substring(ind + 4, ind1 );
    this.userId = x;
    console.log(x);*/

    var x = window.location.pathname;
    var ind = x.lastIndexOf("/");
    x = x.substring(ind + 1, x.length + 1);
    this.userId = x;
    console.log(x);
    axios
      .get(this.port + "/userState/" + this.userId)
      .then((res) => {
        //this.links= res.data;
        console.log(res);
        var x = res.data;
        if (x == 0) {
          window.location.assign("http://localhost:8081");
        }
      })
      .catch((error) => {
        this.errorMessage = error.message;
        console.error("There was an error!", error);
      });

    axios
      .get(this.port + "/getFolders/" + this.userId)
      .then((res) => {
        this.links = res.data;
        console.log(this.links);
      })
      .catch((error) => {
        this.errorMessage = error.message;
        console.error("There was an error!", error);
      });
  },

  data: () => ({
    port: "http://localhost:8080",
    userId: "",
    selectedItem: -1,
    signin: true,
    ok: true,
    links: ["Inbox", "Sent", "Trash", "Draft"],
    //
  }),
  computed() {
    axios
      .get(this.port + "/getFolders/" + this.userId)
      .then((res) => {
        this.links = res.data;
      })
      .catch((error) => {
        this.errorMessage = error.message;
        console.error("There was an error!", error);
      });
  },
  methods: {
    getll(value) {
      return "/" + this.userId + value + "/" + this.userId;
    },

    getUrl2() {
      return "/" + this.userId + "/folders/" + this.userId;
    },
    toggle() {
      this.signin = !this.signin;
    },
    findLink(value) {
      //return "table/"
      window.location.assign(
        "http://localhost:8081" +
          "/" +
          this.userId +
          "/table/" +
          this.userId +
          "/" +
          value +
          "/" +
          this.userId
      );

      return (
        "/" +
        this.userId +
        "/table/" +
        this.userId +
        "/" +
        value +
        "/" +
        this.userId
      );
    },
    getIcon(value) {
      if (value != "f") {
        return "mdi - folder";
      }
      return "mdi - folder";
    },
  },
};
</script>
<style scoped>
.card {
  width: 500px;
  height: 500px;
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
