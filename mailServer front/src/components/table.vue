<template>
  <div class="tab">
    <v-card elevation="2" outlined tile>
      <v-card-title>{{ userfolder }}</v-card-title>

      <v-card-text>
        <v-chip-group
          v-model="selection"
          active-class="deep-purple accent-4 white--text"
          column
        >
          <v-chip large>sort</v-chip>

          <v-chip large>search</v-chip>

          <v-chip large>filter</v-chip>
          <v-chip large>move</v-chip>
        </v-chip-group>
      </v-card-text>
      <v-card>
        <div v-if="selection == 1">
          <div class="text-center">
            <v-text-field class="textf" v-model="searchText"></v-text-field>
            <v-btn class="b" color="orange lighten-2" text @click="search()">
              Search
            </v-btn>
            <v-menu open-on-hover top offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn dark v-bind="attrs" v-on="on">
                  {{ searchType }}
                </v-btn>
              </template>

              <v-list>
                <v-list-item
                  v-for="(item, index) in items"
                  :key="index"
                  @click="menue(item.title)"
                >
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
          <br />
          <br />
        </div>
        <div v-if="selection == 0">
          <div class="text-center">
            <br />
            <v-btn class="b" color="orange lighten-2" text @click="sort()">
              Sort
            </v-btn>
            <v-menu open-on-hover top offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn dark v-bind="attrs" v-on="on">
                  {{ sortType }}
                </v-btn>
              </template>

              <v-list>
                <v-list-item
                  v-for="(item, index) in items2"
                  :key="index"
                  @click="menue2(item.title)"
                >
                  <v-list-item-title>{{ item.title }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
          <br />
          <br />
        </div>

        <div v-if="selection == 3">
          <div class="text-center">
            <br />
            <h2>Select emails to move :</h2>
            <v-btn class="b" color="orange lighten-2" text @click="move()">
              Move
            </v-btn>
            <v-menu open-on-hover top offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn dark v-bind="attrs" v-on="on">
                  {{ moveFolder }}
                </v-btn>
              </template>

              <v-list>
                <v-list-item
                  v-for="(item, index) in items3"
                  :key="index"
                  @click="menue3(item)"
                >
                  <v-list-item-title>{{ item }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
          <br />
          <br />
        </div>

        <div v-if="selection == 2">
          <div class="text-center">
            <v-text-field class="textf" v-model="filterText"></v-text-field>

            <br />
            <v-btn class="b" color="orange lighten-2" text @click="filter()">
              filter
            </v-btn>
            <v-menu open-on-hover top offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn dark v-bind="attrs" v-on="on">
                  {{ filterType }}
                </v-btn>
              </template>

              <v-list>
                <v-list-item
                  v-for="(item, index) in items4"
                  :key="index"
                  @click="menue4(item)"
                >
                  <v-list-item-title>{{ item }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>

            <v-menu open-on-hover top offset-y>
              <template v-slot:activator="{ on, attrs }">
                <v-btn dark v-bind="attrs" v-on="on">
                  {{ filterMove }}
                </v-btn>
              </template>

              <v-list>
                <v-list-item
                  v-for="(item, index) in items3"
                  :key="index"
                  @click="menue5(item)"
                >
                  <v-list-item-title>{{ item }}</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
          <br />
          <br />
        </div>
      </v-card>
    </v-card>

    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="desserts"
      :single-select="singleSelect"
      :hide-default-footer="true"
      item-key="id"
      show-select
      class="elevation-1"
    >
      <template v-slot:top>
        <v-switch
          v-model="singleSelect"
          label="Single select"
          class="pa-3"
        ></v-switch>
        <!-- <v-btn> -->

        <v-btn depressed white class="open" @click="$router.push(openEmail())"
          >open</v-btn
        >
        <!-- </v-btn> -->
        <v-btn depressed white class="open" @click="del()">Delete</v-btn>
        <!-- <v-spacer></v-spacer> -->

        <v-btn
          depressed
          dark
          style="margin-left:700px; margin-right:10px"
          class="red"
          @click="prevPage()"
          >prev Page</v-btn
        >

        <v-btn depressed dark class="red" @click="nextPage()">next Page</v-btn>

        <br />
      </template>
    </v-data-table>
  </div>
</template>
<script>
import axios from "axios";

export default {
  props: ["id", "userfolder"],
  mounted() {
    console.log(this.id);
    console.log(this.userfolder);
    /*axios
      .get(
        this.port +
          "/getEmails/" +
          this.id +
          "/" +
          this.userfolder +
          "/" +
          this.page
      )
      .then((res) => {
        this.desserts = res.data;
        console.log(res[0].priority)
        console.log(this.desserts);
      })
      .catch((error) => {
        this.errorMessage = error.message;
        console.error("There was an error!", error);
      });*/
    fetch(
      this.port +
        "/getEmails/" +
        this.id +
        "/" +
        this.userfolder +
        "/" +
        this.page
    )
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        this.desserts = data;
        console.log(data);
      });
    axios
      .get(this.port + "/getFolders/" + this.id)
      .then((res) => {
        this.items3 = res.data;
      })
      .catch((error) => {
        this.errorMessage = error.message;
        console.error("There was an error!", error);
      });
  },
  methods: {
    menue5(value) {
      this.filterMove = value;
    },
    openEmail() {
      var r = this.selected[0].id;
      console.log(this.selected);
      console.log(r);
      console.log(this.userId);
      window.location.assign(
        "http://localhost:8081/" +
          this.id +
          "/email/" +
          r +
          "/" +
          this.userfolder +
          "/" +
          this.id
      );
      return (
        "/" + this.id + "/email/" + r + "/" + this.userfolder + "/" + this.id
      );
    },
    search() {
      axios
        .get(
          this.port +
            "/search/" +
            this.id +
            "/" +
            this.userfolder +
            "/" +
            this.searchType +
            "/" +
            this.searchText
        )
        .then((res) => {
          this.desserts = res.data;
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
    },
    filter() {
      axios
        .get(
          this.port +
            "/filter/" +
            this.id +
            "/" +
            this.userfolder +
            "/" +
            this.filterType +
            "/" +
            this.filterMove +
            "/" +
            this.filterText
        )
        .then((res) => {
          this.desserts = res.data;
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
    },
    sort() {
      var t;
      if (this.sortType == "Sender") {
        t = 3;
      } else if (this.sortType == "Reciever") {
        t = 4;
      } else if (this.sortType == "Priority") {
        t = 6;
      } else if (this.sortType == "Email") {
        t = 1;
      } else if (this.sortType == "Date") {
        t = 5;
      } else if (this.sortType == "Subject") {
        t = 2;
      }
      axios({
        url:
          this.port +
          "/sort/" +
          this.id +
          "/" +
          this.userfolder +
          "/" +
          t +
          "/" +
          this.page,

        method: "GET",
      }).then((r) => {
        this.desserts = r.data;
        console.log(this.desserts);
      });
    },
    del() {
      var l = [];
      var i;
      var n = this.selected.length;
      for (i = 0; i < n; i++) {
        l.push(this.selected[i].id);
      }
      axios({
        url: this.port + "/deleteEmails/" + this.id + "/" + this.userfolder,

        method: "DELETE",
        data: l,
      }).then(() => {
        console.log("ok");
        axios
          .get(
            this.port +
              "/getEmails/" +
              this.id +
              "/" +
              this.userfolder +
              "/" +
              this.page
          )
          .then((res) => {
            this.desserts = res.data;
            console.log(this.desserts);
          })
          .catch((error) => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
          });
      });
    },
    move() {
      var l = [];
      var i;
      var n = this.selected.length;
      for (i = 0; i < n; i++) {
        l.push(this.selected[i].id);
      }
      axios({
        url:
          this.port +
          "/moveEmails/" +
          this.id +
          "/" +
          this.userfolder +
          "/" +
          this.moveFolder,
        method: "POST",
        data: l,
      }).then(() => {
        console.log("ok");
      });
      axios
        .get(
          this.port +
            "/getEmails/" +
            this.id +
            "/" +
            this.userfolder +
            "/" +
            this.page
        )
        .then((res) => {
          this.desserts = res.data;
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
    },
    nextPage() {
      this.page = this.page + 1;
      axios
        .get(
          this.port +
            "/getEmails/" +
            this.id +
            "/" +
            this.userfolder +
            "/" +
            this.page
        )
        .then((res) => {
          this.desserts = res.data;
        })
        .catch((error) => {
          this.errorMessage = error.message;
          console.error("There was an error!", error);
        });
    },
    prevPage() {
      if (this.page >= 1) {
        this.page = this.page - 1;

        axios
          .get(
            this.port +
              "/getEmails/" +
              this.id +
              "/" +
              this.userfolder +
              "/" +
              this.page
          )
          .then((res) => {
            this.desserts = res.data;
          })
          .catch((error) => {
            this.errorMessage = error.message;
            console.error("There was an error!", error);
          });
      }
    },
    menue(value) {
      this.searchType = value;
    },
    menue2(value) {
      this.sortType = value;
    },
    menue3(value) {
      this.moveFolder = value;
    },
    menue4(value) {
      this.filterType = value;
    },
  },
  data() {
    return {
      filterMove: "Inbox",
      port: "http://localhost:8080",
      searchText: "",
      page: 0,
      moveFolder: "Inbox",
      sortType: "Date",
      searchType: "subject",
      filterType: "subject",
      filterText: "",
      items: [
        { title: "subject" },
        { title: "date" },
        { title: "sender" },
        { title: "reciever" },
      ],
      items2: [
        { title: "Subject" },
        { title: "Date" },
        { title: "Sender" },
        { title: "Reciever" },
        { title: "Priority" },
      ],
      items3: ["Inbox", "Sent", "Trash"],
      items4: ["subject", "sender"],
      folder: "Inbox",
      selection: -1,
      singleSelect: false,
      selected: [],
      headers: [
        {
          text: "subject",
          align: "start",
          sortable: false,
          value: "subject",
        },
        { text: "senderEmail", value: "senderEmail", sortable: false },
        { text: "priority", value: "priority", sortable: false },
      ],
      desserts: [
        {
          name: "Frozen Yogurt",
          calories: 159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          iron: "1%",
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
          fat: 9.0,
          carbs: 37,
          protein: 4.3,
          iron: "1%",
        },
        {
          name: "Eclair",
          calories: 262,
          fat: 16.0,
          carbs: 23,
          protein: 6.0,
          iron: "7%",
        },
        {
          name: "Cupcake",
          calories: 305,
          fat: 3.7,
          carbs: 67,
          protein: 4.3,
          iron: "8%",
        },
        {
          name: "Gingerbread",
          calories: 356,
          fat: 16.0,
          carbs: 49,
          protein: 3.9,
          iron: "16%",
        },
        {
          name: "Jelly bean",
          calories: 375,
          fat: 0.0,
          carbs: 94,
          protein: 0.0,
          iron: "0%",
        },
        {
          name: "Lollipop",
          calories: 392,
          fat: 0.2,
          carbs: 98,
          protein: 0,
          iron: "2%",
        },
        {
          name: "Honeycomb",
          calories: 408,
          fat: 3.2,
          carbs: 87,
          protein: 6.5,
          iron: "45%",
        },
        {
          name: "Donut",
          calories: 452,
          fat: 25.0,
          carbs: 51,
          protein: 4.9,
          iron: "22%",
        },
        {
          name: "KitKat",
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          iron: "6%",
        },
        {
          name: "KitKat",
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          iron: "6%",
        },
        {
          name: "KitKat",
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          iron: "6%",
        },
      ],
    };
  },
};
</script>
<style scoped>
.tab {
  margin-left: 200px;
}
.textf {
  margin-left: 50px;
  margin-right: 50px;
}
.b {
  margin-left: 50px;
  margin-inline: 2px;
}
.open {
  margin-left: 20px;
  width: 200px;
  background-color: darkgray;
}
</style>
