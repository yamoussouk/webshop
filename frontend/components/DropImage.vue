<template>
  <div id="file-drag-drop">
    <form ref="fileform">
      <span class="drop-files">
        <div class="image">
          <img ref="dropicon0" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="0">
          <div ref="preview-wrapper0" class="preview-wrapper">
            <a ref="removebutton0" @click="removeFile( 0 )" class="remove" />
            <img ref="preview0" class="preview">
          </div>
        </div>
        <div class="image">
          <img ref="dropicon1" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="1">
          <div ref="preview-wrapper1" class="preview-wrapper">
            <a ref="removebutton1" @click="removeFile( 1 )" class="remove" />
            <img ref="preview1" class="preview">
          </div>
        </div>
        <div class="image">
          <img ref="dropicon2" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="2">
          <div ref="preview-wrapper2" class="preview-wrapper">
            <a ref="removebutton2" @click="removeFile( 2 )" class="remove" />
            <img ref="preview2" class="preview">
          </div>
        </div>
        <div class="image">
          <img ref="dropicon3" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="3">
          <div ref="preview-wrapper3" class="preview-wrapper">
            <a ref="removebutton3" @click="removeFile( 3 )" class="remove" />
            <img ref="preview3" class="preview">
          </div>
        </div>
        <div class="image">
          <img ref="dropicon4" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="4">
          <div ref="preview-wrapper4" class="preview-wrapper">
            <a ref="removebutton4" @click="removeFile( 4 )" class="remove" />
            <img ref="preview4" class="preview">
          </div>
        </div>
        <div class="image">
          <img ref="dropicon5" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="5">
          <div ref="preview-wrapper5" class="preview-wrapper">
            <a ref="removebutton5" @click="removeFile( 5 )" class="remove" />
            <img ref="preview5" class="preview">
          </div>
        </div>
        <div class="image">
          <img ref="dropicon6" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="6">
          <div ref="preview-wrapper6" class="preview-wrapper">
            <a ref="removebutton6" @click="removeFile( 6 )" class="remove" />
            <img ref="preview6" class="preview">
          </div>
        </div>
        <div class="image">
          <img ref="dropicon7" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="7">
          <div ref="preview-wrapper7" class="preview-wrapper">
            <a ref="removebutton7" @click="removeFile( 7 )" class="remove" />
            <img ref="preview7" class="preview">
          </div>
        </div>
        <div class="image">
          <img ref="dropicon8" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="8">
          <div ref="preview-wrapper8" class="preview-wrapper">
            <a ref="removebutton8" @click="removeFile( 8 )" class="remove" />
            <img ref="preview8" class="preview">
          </div>
        </div>
        <div class="image">
          <img ref="dropicon9" class="drop-file-icon" src="/dropfile.png" alt="drop file here" data-counter="9">
          <div ref="preview-wrapper9" class="preview-wrapper">
            <a ref="removebutton9" @click="removeFile( 9 )" class="remove" />
            <img ref="preview9" class="preview">
          </div>
        </div>
      </span>
    </form>
  </div>
</template>

<script>
export default {
  props: {
    images: {
      type: Array,
      default: () => {
        return []
      }
    },
    alreadyin: {
      type: Array,
      default: () => {
        return []
      }
    },
    productid: {
      type: Number,
      default: 0
    }
  },
  data () {
    return {
      dragAndDropCapable: false,
      uploadPercentage: 100,
      files: []
    }
  },
  mounted () {
    this.dragAndDropCapable = this.determineDragAndDropCapable()
    if (this.$props.alreadyin.length > 0) {
      // const reader = new FileReader()
      for (let i = 0; i < this.$props.alreadyin.length; i++) {
        // reader.readAsDataURL(this.$props.alreadyin[i])
        this.$refs['preview' + parseInt(i)].src = '/uploaded/' + this.productid + '/' + this.$props.alreadyin[i].imageUrl
        // this.$emit('images', this.$props.alreadyin[i])
        this.$refs['dropicon' + parseInt(i)].style.display = 'none'
        // show preview and remove button
        this.$refs['preview' + parseInt(i)].style.height = '100px'
        this.$refs['preview' + parseInt(i)].style.width = '100px'
        this.$refs['preview-wrapper' + parseInt(i)].style.width = '100px'
        this.$refs['preview-wrapper' + parseInt(i)].style.height = '100px'
        this.$refs['removebutton' + parseInt(i)].text = 'X'
        this.files.push(this.$props.alreadyin[i])
      }
    }
    if (this.dragAndDropCapable) {
      ['drag', 'dragstart', 'dragend', 'dragover', 'dragenter', 'dragleave', 'drop'].forEach(function (evt) {
        this.$refs.fileform.addEventListener(evt, function (e) {
          e.preventDefault()
          e.stopPropagation()
        }, false)
      }.bind(this))
      this.$refs.fileform.addEventListener('drop', function (e) {
        for (let i = 0; i < e.dataTransfer.files.length; i++) {
          this.files.push(e.dataTransfer.files[i])
        }
        this.getImagePreviews(e)
      }.bind(this))
    }
  },
  methods: {
    determineDragAndDropCapable () {
      const div = document.createElement('div')
      return (('draggable' in div) ||
          ('ondragstart' in div && 'ondrop' in div)) &&
          'FormData' in window &&
          'FileReader' in window
    },
    getImagePreviews (event) {
      const counter = event.target.attributes[2].value
      console.log(this.files)
      if (/\.(jpe?g|png|gif)$/i.test(this.files[counter].name)) {
        const reader = new FileReader()

        reader.addEventListener('load', function () {
          this.$refs['preview' + parseInt(counter)].src = reader.result
          this.files[counter].readerresult = reader.result
          // this.addImage(this.files[counter])
          // this.$emit('images', this.files[counter - 1])
          this.$emit('images', this.files[counter])
        }.bind(this), false)
        reader.readAsDataURL(this.files[counter])
        // hide dropdown icon
        this.$refs['dropicon' + parseInt(counter)].style.display = 'none'
        // show preview and remove button
        this.$refs['preview' + parseInt(counter)].style.height = '100px'
        this.$refs['preview' + parseInt(counter)].style.width = '100px'
        this.$refs['preview-wrapper' + parseInt(counter)].style.width = '100px'
        this.$refs['preview-wrapper' + parseInt(counter)].style.height = '100px'
        this.$refs['removebutton' + parseInt(counter)].text = 'X'
      } else {
        this.$nextTick(function () {
          this.$refs['preview' + parseInt(counter)].src = '/images/file.png'
        })
      }
    },
    resetImages () {
      for (let i = 0; i < 10; i++) {
        this.$refs['dropicon' + parseInt(i)].style.display = 'block'
        this.$refs['preview' + parseInt(i)].style.height = '0px'
        this.$refs['preview' + parseInt(i)].style.width = '0px'
        this.$refs['preview-wrapper' + parseInt(i)].style.width = '0px'
        this.$refs['preview-wrapper' + parseInt(i)].style.height = '0px'
        this.$refs['removebutton' + parseInt(i)].text = ''
        this.$refs['preview' + parseInt(i)].src = '/dropfile.png'
      }
    },
    removeFile (key) {
      this.$emit('remove', this.files[key])
      this.files.splice(key, 1)
      this.recalculate()
    },
    resetDropIcons () {
      for (let i = 0; i < 10; i++) {
        this.$refs['preview' + i].src = ''
        this.$refs['preview' + i].style.width = '0px'
        this.$refs['preview' + i].style.height = '0px'
        this.$refs['preview-wrapper' + i].style.width = '0px'
        this.$refs['preview-wrapper' + i].style.height = '0px'
        this.$refs['removebutton' + i].text = ''
        this.$refs['dropicon' + i].style.display = 'block'
      }
    },
    recalculate () {
      this.resetDropIcons()
      for (let i = 0; i < this.files.length; i++) {
        const src = this.productid === 0 || this.files[i].imageUrl === undefined ? this.files[i].readerresult : '/uploaded/' + this.productid + '/' + this.files[i].imageUrl
        this.$refs['preview' + parseInt(i)].src = src
        this.$refs['dropicon' + parseInt(i)].style.display = 'none'
        this.$refs['preview' + parseInt(i)].style.height = '100px'
        this.$refs['preview' + parseInt(i)].style.width = '100px'
        this.$refs['preview-wrapper' + parseInt(i)].style.width = '100px'
        this.$refs['preview-wrapper' + parseInt(i)].style.height = '100px'
        this.$refs['removebutton' + parseInt(i)].text = 'X'
      }
    },
    submitFiles () {
      const formData = new FormData()
      for (let i = 0; i < this.files.length; i++) {
        const file = this.files[i]

        formData.append('files[' + i + ']', file)
      }
    }
  }
}
</script>

<style>
  form {
    display: block;
    height: auto;
    width: 100%;
  }
  .drop-file-icon {
    width: 100px;
    height: auto;
  }
  .drop-files .image {
    height: 105px;
  }
  .preview {
    margin-bottom: 5%;
  }
  div.file-listing {
    width: 400px;
    margin: auto;
    padding: 10px;
    border-bottom: 1px solid #ddd;
  }

  div.file-listing img{
    height: 100px;
  }
  a.submit-button{
    display: block;
    margin: auto;
    text-align: center;
    width: 200px;
    padding: 10px;
    text-transform: uppercase;
    background-color: #CCC;
    color: white;
    font-weight: bold;
    margin-top: 20px;
  }
  progress{
    width: 400px;
    margin: auto;
    display: block;
    margin-top: 20px;
    margin-bottom: 20px;
  }
  .preview-wrapper {
    position: relative;
  }
  .preview-wrapper a {
    position: absolute;
    top: 0;
    right: 10px;
  }
  .drop-files .image {
    float: left;
  }
  .remove {
    cursor: pointer;
  }
</style>
