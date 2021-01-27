<template>
  <div class="email">
    <v-card hover>
      <v-card-text>
        <p class="body-1">subject:</p>
        <p class="display-2 text--primary">
          {{ email.subject }}
        </p>
        <p class="body-1">priority:</p>
        <p class="display-2 text--primary">
          {{ email.priority }}
        </p>
        <p class="body-1">sender:</p>
        <p class="subtitle-2 text--primary">
          {{ email.senderEmail }}
        </p>
        <p class="body-1">recievers:</p>
        <p class="subtitle-2 text--primary">
          {{ email.receiverEmail }}
        </p>

        <p class="body-1">body:</p>
        <div class="display-1 text--primary">
          {{ email.body }}
        </div>
        <br />
        <p class="body-1" v-if="att.length > 0">attatchments:</p>

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
              view attachment
            </v-btn>
          </template>

          <v-list>
            <v-list-item
              v-for="(item, index) in items"
              :key="index"
              @click="openatt(item)"
            >
              <v-list-item-title>{{ item }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "email",
  props: ["id", "selectedEmail", "folderName"],
  components: {},
  mounted() {
    axios
      .get(
        this.port +
          "/getOneEmail/" +
          this.id +
          "/" +
          this.folderName +
          "/" +
          this.selectedEmail
      )
      .then((res) => {
        this.email = res.data;
        this.items = this.email.attachments;
      })
      .catch((error) => {
        this.errorMessage = error.message;
        console.error("There was an error!", error);
      });
  },
  methods: {
    openatt(item) {
      console.log(item);
      console.log(this.selectedItem);
      fetch("http://localhost:8080/download/" + item)
        .then((r) => r.blob())
        .then(function(data) {
          var elem = window.document.createElement("a");
          elem.href = window.URL.createObjectURL(data);
          elem.download = item;
          document.body.appendChild(elem);
          elem.click();
          document.body.removeChild(elem);
        });
    },
  },
  data: () => ({
    selectedItem: -1,
    port: "http://localhost:8080",

    att: [],
    email: [],

    //
  }),
};
</script>
<style scoped>
.email {
  margin-top: 50px;
  margin-left: 300px;
  padding-left: 40px;
  width: 900px;
}
</style>
