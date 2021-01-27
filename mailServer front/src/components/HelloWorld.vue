<template>
  <nav>
    <v-toolbar flat app> </v-toolbar>

    <v-navigation-drawer app v-model="drawer" dark class="primary">
      <br />
      <br />
      <br />
      <ul>
        <li v-for="link in links" :key="link.text">
          <router-link :to="findLink(link)" class="link"
            >{{ link.text }}
          </router-link>
        </li>
      </ul>
      <!-- <v-list class="pl">
        <v-list-tile v-for="link in links" :key="link.text" router to="/play">
          <v-list-tile-action> </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title class="white--text">{{
              link.text
            }}</v-list-tile-title>
          </v-list-tile-content>
          <br />
          <br />
          <br />
        </v-list-tile>
        <br />
      </v-list> -->
      <br />
      <br />
      <br />
    </v-navigation-drawer>
    <v-container class="ppli">
      <router-view></router-view>
    </v-container>
  </nav>
</template>

<script>
export default {
  methods: {
    findLink(value) {
      return value.link;
    },
  },
  data() {
    return {
      drawer: true,
      links: [
        { icon: "dashboard", text: "Dashboard", route: "/" },
        { icon: "email", text: "My Projects", route: "/play" },
        { icon: "person", text: "table", route: "/table" },
      ],
    };
  },
};
</script>

<style>
.pl {
  margin: 40px;
}
.p {
  margin: 200px;
}
.ppli {
  margin-left: 400px;
  padding-left: 40px;
}
.link {
  color: #ffff;
  background-color: black;
}
</style>
