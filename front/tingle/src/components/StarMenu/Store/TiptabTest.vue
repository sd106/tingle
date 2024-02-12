<template>
  <main>
    <link
  href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
  rel="stylesheet"/>

    <div v-if="editor" class="tw-flex tw-flex-wrap tw-gap-2">
      <!-- Bold -->
      <button @click="editor.chain().focus().toggleBold().run()" :disabled="!editor.can().chain().focus().toggleBold().run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('bold') }">
        <i class="fas fa-bold"></i>
      </button>
      
      <!-- Italic -->
      <button @click="editor.chain().focus().toggleItalic().run()" :disabled="!editor.can().chain().focus().toggleItalic().run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('italic') }">
        <i class="fas fa-italic"></i>
      </button>
      
      <!-- Strike -->
      <button @click="editor.chain().focus().toggleStrike().run()" :disabled="!editor.can().chain().focus().toggleStrike().run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('strike') }">
        <i class="fas fa-strikethrough"></i>
      </button>
      
      <!-- Clear Marks -->
      <button @click="editor.chain().focus().unsetAllMarks().run()" class="tw-btn">
        <i class="fas fa-eraser"></i>
      </button>
      
      <!-- Clear Nodes -->
      <!-- <button @click="editor.chain().focus().clearNodes().run()" class="tw-btn">
        <i class="fas fa-ban"></i>
      </button>
       -->
      <!-- Paragraph -->
      <!-- <button @click="editor.chain().focus().setParagraph().run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('paragraph') }">
        <i class="fas fa-paragraph"></i>
      </button> -->
      
      <!-- Heading 1 -->
      <button @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('heading', { level: 1 }) }">
        <i class="fas fa-heading"></i> H1
      </button>
      
      <!-- Heading 2 -->
      <button @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('heading', { level: 2 }) }">
        <i class="fas fa-heading"></i> H2
      </button>
      
      <!-- Heading 3 -->
      <button @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('heading', { level: 3 }) }">
        <i class="fas fa-heading"></i> H3
      </button>
      
      <!-- <button @click="editor.chain().focus().toggleBulletList().run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('bulletList') }">
        <i class="fas fa-list-ul"></i>
      </button> -->
      
      <!-- <button @click="editor.chain().focus().toggleOrderedList().run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('orderedList') }">
        <i class="fas fa-list-ol"></i>
      </button>
      
      <button @click="editor.chain().focus().toggleBlockquote().run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('blockquote') }">
        <i class="fas fa-quote-right"></i>
      </button>
      
      <button @click="editor.chain().focus().toggleCodeBlock().run()" class="tw-btn" :class="{ 'tw-bg-gray-300': editor.isActive('codeBlock') }">
        <i class="fas fa-code"></i>
      </button>
      
      <button @click="editor.chain().focus().setHorizontalRule().run()" class="tw-btn">
        <i class="fas fa-minus"></i>
      </button>

      <button @click="editor.chain().focus().setHardBreak().run()" class="tw-btn">
        <i class="fas fa-arrow-down"></i>
      </button>
       -->
      <button @click="editor.chain().focus().undo().run()" :disabled="!editor.can().chain().focus().undo().run()" class="tw-btn">
        <i class="fas fa-undo-alt"></i>
      </button>
      
      <button @click="editor.chain().focus().redo().run()" :disabled="!editor.can().chain().focus().redo().run()" class="tw-btn">
        <i class="fas fa-redo-alt"></i>
      </button>
    </div>
    <div class="mt-2 editor-container p-0 tw-border tw-rounded tw-p-2 tw-border-gray-300">
      <editor-content :editor="editor"/>
    </div>
  </main>
</template>

<script setup lang="ts">

import { useEditor, EditorContent } from '@tiptap/vue-3'
import StarterKit from '@tiptap/starter-kit'
import { watch } from 'vue';

const props = defineProps({
  modelValue: {
    type: String,
    defalt: '',
  },
});

const emit = defineEmits({
  'update:modelValue': (value: string) => true
});


const editor = useEditor({
  content: props.modelValue,
  extensions: [
    StarterKit,
  ],
  onUpdate: ({editor}) => {
      emit('update:modelValue', editor.getHTML())
    }
})

watch(() => props.modelValue, (newValue) => {
  if (newValue !== undefined && editor.value) {
    editor.value.commands.setContent(newValue, false);
  }
});


</script>

<style>

.editor-container .ProseMirror{
  /* 에디터의 너비와 높이를 설정 */
  width: 100%; /* 또는 특정 너비(px, em 등) */
  height: 200px; /* 초기 높이 설정 */
  border: 1px solid #ccc; /* 에디터 경계선 스타일 */
  border-radius: 5px; /* 경계선 둥글게 */
  padding: 10px; /* 내부 여백 */
  overflow-y: auto; /* 내용이 넘칠 경우 세로 스크롤바 표시 */
}

</style>