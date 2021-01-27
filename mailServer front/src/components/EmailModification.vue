<template>
  <v-app class="whole">
    <v-container>
      <v-text-field label="New E-Mail"></v-text-field>
      <v-btn depressed elevation="10" outlined rounded x-large>
        Add E-Mail
      </v-btn>
    </v-container>
    <v-container>
      <v-menu>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            depressed
            elevation="10"
            outlined
            rounded
            x-large
            v-bind="attrs"
            v-on="on"
          >
            Current E-Mails
          </v-btn>
        </template>

        <v-list>
          <v-list-item v-for="(item, index) in currentEMAils" :key="index">
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-container>
    <v-container v-if="!passwordModification">
      <v-btn
        depressed
        elevation="10"
        outlined
        rounded
        x-large
        @click="toggle()"
      >
        Password Settings
      </v-btn>
    </v-container>
    <v-container v-if="passwordModification">
      <v-text-field label="Enter Old Password"></v-text-field>
      <v-text-field label="Enter New Password"></v-text-field>
      <v-btn depressed elevation="10" outlined rounded x-large>
        Change Password
      </v-btn>
    </v-container>
  </v-app>
</template>

<script>
export default {
  name: "EmailModification",

  components: {},

  data: () => ({
    //
    passwordModification: false,
  }),
  methods: {
    toggle() {
      this.passwordModification = true;
      console.log(this.passwordModification);
    },
  },
};
</script>
<style scoped>
.table {
  margin-left: 400px;
  padding-left: 40px;
}
.whole {
  margin-left: 20%;
}
</style>
