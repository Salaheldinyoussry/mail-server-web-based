<template>
  <div class="email">
    <a href="http://localhost:8081/compose">link text</a>

    <!-- <v-btn @click="down()">fffffff</v-btn>
    <input type="file" id="myfile" />ype
    <v-btn @click="up()">upload</v-btn> -->
    <v-btn @click="trydel()">try del</v-btn>

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
    <v-list flat>
      <v-subheader>attatchments</v-subheader>
      <v-list-item-group v-model="selectedItem" color="primary">
        <v-list-item v-for="(item, i) in list" :key="i">
          <v-list-item-content>
            <v-list-item-title
              @click="down()"
              v-text="item"
            ></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "email",
  props: ["selectedEmail"],
  methods: {
    trydel() {
      axios({
        url: "http://localhost:8080/hehe",
        method: "PUT",
        data: ["dd", "dodo", "lolo"],
      });
    },
    onUpload() {
      let file = this.$refs.uploadFile.files[0];
      this.formdata = new FormData();
      this.formdata.append("file", file);
    },
    up() {
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

      // const ff = document.getElementById("myfile");
      // this.formdata = new FormData();
      // this.formdata.append("file", ff.files[0]);
      // console.log(ff.files);
      // this.list.push(ff.files[0].name);
      // fetch("http://localhost:8089/upload", {
      //   method: "POST",
      //   headers: {
      //     Accept: "application/json",
      //     "Access-Control-Allow-Origin": "*",
      //     "Content-Type": " multipart/form-data; boundary=FBFJE-EEF3",
      //     mode: "no-cors",
      //   },
      // }).then(() => {});

      // this.$http.post("http://localhost:8089/upload", this.formdata, {
      //   headers: {
      //     "Content-Type": "multipart/form-data",
      //   },
      // });
      //       var elem = window.document.createElement("a");
      // elem.href = window.URL.createObjectURL(data);
      // elem.download = filename;
      // document.body.appendChild(elem);
      // elem.click();
      // document.body.removeChild(elem);
    },
    down() {
      fetch("http://localhost:8080/download/hey man.txt")
        .then((r) => r.blob())
        .then(function(data) {
          // let filename = needjson ? "canvas.json" : "canvas.xml";
          // if (window.navigator.msSaveOrOpenBlob) {
          //   window.navigator.msSaveBlob(data, filename);
          // } else {
          var elem = window.document.createElement("a");
          elem.href = window.URL.createObjectURL(data);
          elem.download = "java.rar";
          document.body.appendChild(elem);
          elem.click();
          document.body.removeChild(elem);
          //}
        });
      // window.location.assign("http://localhost:8081/compose");
      //   var elem = window.document.createElement("a");
      //   elem.href = window.URL.createObjectURL(this.formdata);
      //   elem.download = "ff";
      //   document.body.appendChild(elem);
      //   elem.click();
      //   document.body.removeChild(elem);
      // const ff = document.getElementById("myfile");
      // saveAs(ff.files[0], ff.files[0].name);
    },
  },
  components: {},
  mounted() {
    console.log(this.selectedEmail);
  },

  data: () => ({
    mail: null,
    selectedItem: -1,
    index: 0,
    list: [],
    formdata: null,
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
