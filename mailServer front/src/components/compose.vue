<template>
  <div class="tab">
    <v-container>
      <v-text-field
        label=" Reciever E-Mail"
        v-model="recieverEmail"
      ></v-text-field>
      <v-btn
        depressed
        elevation="10"
        outlined
        rounded
        x-large
        @click="addReciever(recieverEmail)"
        >Add Reciever</v-btn
      >
    </v-container>
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
          {{ priority }}
        </v-btn>
      </template>

      <v-list>
        <v-list-item v-for="(item, index) in priorites" :key="index">
          <v-btn
            depressed
            elevation="10"
            outlined
            rounded
            x-large
            v-bind="attrs"
            v-on="on"
            v-model="sortType"
            @click="changePriority(item)"
          >
            {{ item.title }}
          </v-btn>
        </v-list-item>
      </v-list>
    </v-menu>
    <!-- <v-container>
        <v-chip color="blue" label outlined x-large>Attachments</v-chip>
        <v-btn
          depressed
          elevation="10"
          outlined
          rounded
          x-large
          v-bind="attrs"
          v-on="on"
        >
          choose Attachments
        </v-btn>
      </v-container> -->
    <v-container>
      <v-text-field label=" Subject" v-model="subject"></v-text-field>
    </v-container>
    <v-container>
      <v-textarea
        autocomplete="email"
        label="Email"
        v-model="body"
      ></v-textarea>
    </v-container>

    <form ref="uploadForm" @submit.prevent="submit">
      <input
        type="file"
        ref="uploadFile"
        @change="onUpload()"
        class="form-control"
        required
      />
      <v-btn @click="up()">upload</v-btn>
    </form>

    <v-btn
      class="b"
      color="orange lighten-2"
      text
      @click="del()"
      v-if="items.length > 0"
    >
      delete
    </v-btn>

    <v-card hover style="width:400px" v-if="items.length > 0">
      <v-list flat>
        <v-list-item-group v-model="selectedItem" color="primary">
          <v-list-item v-for="(item, i) in items" :key="i">
            <v-list-item-content>
              <v-list-item-title v-text="item.name"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-card>
    <br />
    <v-container>
      <v-btn
        depressed
        elevation="10"
        outlined
        rounded
        x-large
        @click="sendEmail()"
      >
        Send</v-btn
      >
      <v-btn
        depressed
        elevation="10"
        outlined
        rounded
        x-large
        @click="saveDraft()"
      >
        Save as a draft</v-btn
      >
      <v-btn
        depressed
        elevation="10"
        outlined
        rounded
        x-large
        @click="showDrafts()"
      >
        Load draft</v-btn
      >
    </v-container>
    <v-container v-if="showDraft">
      <v-data-table
        v-model="selected"
        :headers="headers"
        :items="drafts"
        :single-select="true"
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
        </template>
      </v-data-table>
    </v-container>
    <v-btn
      depressed
      elevation="10"
      outlined
      rounded
      x-large
      @click="loadspecificDraft()"
    >
      Load selected Draft</v-btn
    >
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "compose",
  props: ["id"],
  components: {},

  data: () => ({
    port: "http://localhost:8080",
    showDraft: false,
    fileName: "",
    selectedItem: -1,
    selected: [],
    formdata: null,
    items: [],
    att: [],
    EMAil: [],
    loadedDraft: null,
    drafts: [
      { receiverEmail: "aknfdoskn@saf", priority: "2", body: "a7a" },
      { receiverEmail: "aknfdoskn@af", priority: "2", body: "a7a" },
    ],
    headers: [
      {
        text: "reciever Email",
        align: "start",
        sortable: false,
        value: "receiverEmail",
      },
      { text: "Subject", value: "subject" },
      { text: "priority", value: "priority" },
      { text: "body", value: "body" },
    ],
    recieverEmail: "",
    recieverEmails: [],
    emailsCounter: 0,
    subject: "",
    body: "",
    priority: 0,
    priorites: [
      { title: "1", value: 1 },
      { title: "2", value: 2 },
      { title: "3", value: 3 },
    ],

    //
  }),
  methods: {
    loadspecificDraft() {
      var selecteddraft = this.selected[0].id;
      var userId = this.id;
      console.log(userId);
      console.log(selecteddraft);
      this.LoadSingleDraft(userId, selecteddraft);
    },
    showDrafts() {
      var userId = this.id;
      this.showDraft = true;
      this.LoadDraftsFetch(userId);
    },
    addReciever() {
      this.recieverEmails[0] = this.recieverEmail;
    },
    sendEmail() {
      var recievers = this.recieverEmail;
      var Body = this.body;
      var Subject = this.subject;
      var Priority = this.priority;
      var userId = this.id;

      //call send fetch
      this.SendEmailFetch(recievers, Body, Subject, userId, Priority, this.att);
      console.log(userId);
      console.log(recievers);
      console.log(Body);
      console.log(Subject);
      console.log(Priority);
    },
    saveDraft() {
      var recievers = this.recieverEmails[0];
      var Body = this.body;
      var Subject = this.subject;
      var Priority = this.priority;
      var userId = this.id;
      this.SaveDraftFetch(recievers, Body, Subject, userId, Priority);
    },

    changePriority(item) {
      this.priority = item.value;
    },

    del() {
      var index = this.selectedItem;
      this.items.splice(index, 1);
      var ranNam = this.att[index];
      this.att.splice(index, 1);
      axios({
        url: this.port + "/deleteAttachment/" + ranNam,
        method: "DELETE",
      }).then((r) => {
        console.log(r);
      });
    },

    onUpload() {
      let file = this.$refs.uploadFile.files[0];
      this.fileName = file.name;

      var r = Math.floor(Math.random() * 100000000000);
      var ranName = "" + r + "_" + this.fileName;
      // file.name = ranName;
      this.rname = ranName;
      this.formdata = new FormData();
      this.formdata.append("file", file, ranName);
    },
    up() {
      var x = { name: "" };
      x.name = this.fileName;
      this.items.push(x);

      this.att.push(this.rname);

      axios({
        url: "http://localhost:8080/upload",
        method: "POST",
        data: this.formdata,
        headers: {
          Accept: "application/json",
          "Content-Type": "multipart/form-data",
        },
      }).then(() => {
        console.log("ok");
      });
    },
    getEmailInShape(recievers, Body, Subject, senderId, priority, attachment) {
      var e = {
        senderID: senderId,
        subject: Subject,
        receiverEmail: recievers,
        body: Body,
        priority: priority,
        attachments: attachment,
      };
      return e;
    },
    SendEmailFetch(recievers, Body, Subject, senderId, priority, attachment) {
      console.log(recievers);
      return fetch("http://localhost:8080/compose", {
        method: "post",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        body: JSON.stringify(
          this.getEmailInShape(
            recievers,
            Body,
            Subject,
            senderId,
            priority,
            attachment
          )
        ),
      });
    },

    SaveDraftFetch(recievers, Body, Subject, senderId, priority) {
      return fetch("http://localhost:8080/compose/saveDraft", {
        method: "post",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
        body: JSON.stringify(
          this.getEmailInShape(recievers, Body, Subject, senderId, priority)
        ),
      })
        .then((response) => response.json())
        .then((body) => {
          console.log(body);
          console.log("sucessedSave");
        });
    },
    LoadDraftsFetch(userId) {
      return fetch("http://localhost:8080/compose/loadDraftList/" + userId, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Accept: "application/json",
        },
      })
        .then((response) => response.json())
        .then((body) => {
          this.drafts = body;
          console.log(body);
          console.log("sucessedLoaded");
        });
    },
    LoadSingleDraft(userId, draftId) {
      return fetch(
        "http://localhost:8080/compose/loadDraft/" + userId + "/" + draftId,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        }
      )
        .then((response) => response.json())
        .then((body) => {
          this.loadedDraft = body;
          this.priority = this.loadedDraft.priority;
          this.subject = this.loadedDraft.subject;
          this.recieverEmail = this.loadedDraft.receiverEmail;
          this.body = this.loadedDraft.body;
          console.log(body);
          console.log("sucessedLoaded");
        });
    },
  },
};
</script>
<style scoped>
.sender {
  align-self: center;
  margin-left: 20%;
}
.table {
  margin-left: 400px;
  padding-left: 40px;
}
.tab {
  margin-left: 200px;
}
</style>
