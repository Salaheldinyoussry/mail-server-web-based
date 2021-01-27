<template>
  <div class="tab">
    <v-container>
      <v-text-field label=" Search" v-model="searchText"></v-text-field>
      <v-btn
        depressed
        elevation="10"
        outlined
        rounded
        x-large
        @click="search()"
      >
        Search
      </v-btn>

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
            search by
          </v-btn>
        </template>

        <v-list>
          <v-list-item
            v-for="(item, index) in searchType"
            :key="index"
            @click="searchMenue(item)"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
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
            Sort by
          </v-btn>
        </template>

        <v-list>
          <v-list-item
            v-for="(item, index) in sortType"
            :key="index"
            @click="sortMenue(item)"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <v-btn
        depressed
        elevation="10"
        outlined
        rounded
        x-large
        v-bind="attrs"
        v-on="on"
        @click="sort()"
      >
        Sort
      </v-btn>
    </v-container>
    <v-container>
      <h4>
        to add contact you enter his/her email if the user exist in database
        it's informaion will be saved in your contacts
      </h4>
      <v-text-field label="Contact Email" v-model="ce"></v-text-field>
      <!-- <v-text-field label="Contact last name" v-model="cl"></v-text-field>

      <v-text-field label="E-Mail" v-model="ce"></v-text-field> -->
      <v-btn depressed elevation="10" outlined rounded x-large @click="addc()">
        Add Contact
      </v-btn>
    </v-container>
    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="Contacts"
      :single-select="singleSelect"
      item-key="first_name"
      show-select
      class="elevation-1"
    >
      <template v-slot:top>
        <v-switch
          v-model="singleSelect"
          label="Single select"
          class="pa-3"
        ></v-switch>
      </template>
    </v-data-table>
    <v-btn depressed elevation="10" outlined rounded x-large>
      Delete Selected
    </v-btn>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "Contacts",
  props: ["id"],

  components: {},
  mounted() {
    //@GetMapping("getContacts/{userID}")

    axios
      .get(this.port + "/getContacts/" + this.id)
      .then((res) => {
        this.Contacts = res.data;
      })
      .catch((error) => {
        this.errorMessage = error.message;
        console.error("There was an error!", error);
      });
  },
  methods: {
    sortMenue(value) {
      this.sortValue = value.value;
    },
    searchMenue(value) {
      this.searchValue = value.value;
    },
    search() {
      axios
        .get(
          this.port +
            "/searchContacts/" +
            this.id +
            "/" +
            this.searchText +
            "/" +
            this.searchValue
        )
        .then((res) => {
          this.Contacts = res.data;
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
    },
    sort() {
      axios
        .get(this.port + "/sortContacts/" + this.id + "/" + this.sortValue)
        .then((res) => {
          this.Contacts = res.data;
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
    },
    addc() {
      // if (this.ce != "" && this.cn != "" && this.cl) {
      //   var x = { first_name: "", last_name: "", email: "" };
      //   x.first_name = this.cn;
      //   x.last_name = this.cl;
      //   x.email = this.ce;
      //   this.Contacts.push(x);
      // }
      if (this.ce != "") {
        axios
          .get(this.port + "/Contacts/addContact/" + this.id + "/" + this.ce)
          .then((res) => {
            console.log(this.id);
            this.Contacts = res.data;
          })
          .catch((error) => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
          });
      }
    },
    deletec() {},
  },

  data: () => ({
    cn: "",
    cl: "",
    ce: "",
    port: "http://localhost:8080",
    sortValue: 0,
    searchValue: "",
    searchText: "",
    searchType: [
      { title: "firstName", value: "firstName" },
      { title: "Email", value: "Email" },
    ],
    sortType: [
      { title: "firstName", value: 0 },
      { title: "Email", value: 1 },
    ],
    AddEmail: false,
    singleSelect: true,
    selected: [],
    headers: [
      { text: "First Name", value: "first_name" },
      { text: "Last Name", value: "last_name" },

      { text: "MainEmailAddress", value: "email" },
    ],
    Contacts: [
      {
        first_name: "Frozen Yogurt",
        last_name: "ddd",
        email: "kkk",
      },
      {
        first_name: " Yogurt",
        last_name: "ddd",
        email: "kkk",
      },
    ],
  }),
};
</script>
<style scoped>
.table {
  margin-left: 400px;
  padding-left: 40px;
}
.tab {
  margin-left: 200px;
}
</style>
