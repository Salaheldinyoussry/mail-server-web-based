<template>
  <div class="email">
    <!-- <v-card hover> -->
    <v-card hover style="width:400px">
      <v-list flat>
        <v-subheader>FOLDERS</v-subheader>
        <v-list-item-group v-model="selectedItem" color="primary">
          <v-list-item v-for="(item, i) in items" :key="i">
            <v-list-item-content>
              <v-list-item-title>{{ item }}</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>

      <br />
      <v-text-field
        label="new folder or rename"
        style="width:250px; margin-left:20px;"
        v-model="text"
      ></v-text-field>

      <v-btn
        class="b"
        color="orange lighten-2"
        text
        @click="add()"
        style="margin-bottom:20px"
      >
        add
      </v-btn>
      <v-btn
        class="b"
        color="orange lighten-2"
        text
        @click="rename()"
        style="margin-bottom:20px"
      >
        rename
      </v-btn>
      <v-btn
        class="b"
        color="orange lighten-2"
        text
        @click="del()"
        style="margin-bottom:20px"
      >
        delete
      </v-btn>
      <v-btn dark @click="refresh()" style="margin-bottom:20px">
        Done
      </v-btn>
      <br />
      <h5 style="margin-bottom:100px">
        after renaming a folder deselect it to see the new Name
      </h5>
    </v-card>
    <!-- </v-card> -->
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "folderManipulation",
  props: ["id"],
  components: {},
  mounted() {
    axios({
      url: this.port + "/getFolders/" + this.id,
      method: "GET",
    }).then((r) => {
      this.f = r.data;
      console.log(this.f);
      console.log("ok");
      var i;
      var t = [];
      var n = this.f.length;

      for (i = 0; i < n; i++) {
        if (
          (this.f[i] != "Inbox") &
          (this.f[i] != "Trash") &
          (this.f[i] != "Sent") &
          (this.f[i] != "Draft")
        ) {
          t.push(this.f[i]);

          //}
        }
        // }
      }
      this.items = t;
      console.log(this.items);
    });
  },
  methods: {
    refresh() {
      window.location.assign("http://localhost:8081/" + this.id);
    },
    del() {
      var index = this.selectedItem;
      axios({
        url: this.port + "/removeFolder/" + this.id + "/" + this.items[index],
        method: "DELETE",
      }).then(() => {
        console.log("ok");
      });

      this.items.splice(index, 1);
    },
    rename() {
      var index = this.selectedItem;
      var name = this.text;
      //var oldName;
      var e = false;
      var nn = this.items.length;
      var i;
      for (i = 0; i < nn; i++) {
        if (this.items[i] == name) {
          e = true;
          alert("name already exist!");
          break;
        }
      }

      if (name != "" && index != -1 && !e) {
        var oldName = this.items[index];
        this.items[index] = name;
        axios({
          url:
            this.port + "/renameFolder/" + this.id + "/" + oldName + "/" + name,
          method: "PUT",
        }).then(() => {
          console.log("ok");
        });
      }
    },
    add() {
      var x;
      x = this.text;

      var e = false;
      var nn = this.items.length;
      var i;
      for (i = 0; i < nn; i++) {
        if (this.items[i] == x) {
          e = true;
          alert("name already exist!");

          break;
        }
      }
      if (!e) {
        this.items.push(x);

        axios({
          url: this.port + "/addFolder/" + this.id + "/" + x,
          method: "PUT",
        }).then(() => {
          console.log("ok");
        });
      }
    },
  },

  data: () => ({
    text: "",
    selectedItem: -1,
    mail: null,
    port: "http://localhost:8080",
    f: ["inbox", "sent", "trash", "folo"],

    items: [],

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
.b {
  margin-left: 50px;
  margin-inline: 2px;
}
</style>
