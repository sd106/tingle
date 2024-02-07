<template>
  <main class="container">
    <h1>Test스토어</h1>
    <hr>
    <!-- //-----------스타의 상품 상세 조회----------------------------// -->
    <div>
      <h1>상품 상세 조회</h1>
      <form @submit.prevent="submitFormGetProductById">
        <input type="number" v-model="inputProductId" placeholder="상품 ID 입력">
        <button type="submit">상품 상세 조회</button>
      </form>



      <div v-if="productgetById">
        <p>상품 이름: {{ productgetById.data.name }}</p>
        <p>스타 이름: {{ productgetById.data.starName }}</p>
        <p>수량: {{ productgetById.data.amount }}</p>
        <p>가격: {{ productgetById.data.price }}</p>
        <p>설명: {{ productgetById.data.content }}</p>
        <p>사용 가능: {{ productgetById.data.available ? '예' : '아니오' }}</p>
        <div v-if="productgetById.data.imageUrl && productgetById.data.imageUrl.length">
          <h3>이미지:</h3>
          <div v-for="url in productgetById.data.imageUrl" :key="url">
            <img :src="url.url" alt="Product Image" style="max-width: 200px; margin: 5px;">
            <button @click="updateImage(productgetById.data.productId, url.id)">수정하기</button>
            <button @click="deleteImage(url.id, productgetById.data.productId)">삭제하기</button>
          </div>
        </div>
        <button @click="uploadImage(productgetById.data.productId)">업로드</button>
      </div>
    </div>
    <!-- //-----------스타의 상품 상세 조회----------------------------// -->





    <!-- //-----------스타의 상품 조회----------------------------// -->
    <hr>
    <br>
    <hr>
    <h1>스타의 상품 조회</h1>
    <div>
      <form @submit.prevent="submitFormgetByStarName">
        <input type="text" v-model="inputStarName" placeholder="스타 이름 입력">
        <button type="submit">스타 상품 목록 조회</button>
      </form>
      <div v-if="productgetByStarName">
        <div v-for="product in productgetByStarName" :key="product">
          <p>{{ product }}</p>
          <!-- 기타 상품 정보 표시 -->
        </div>
        <!-- <h3>{{ productgetByStarName }}</h3> -->
      </div>
      <div v-else>
        스타의 상품 목록이 없습니다.
      </div>
    </div>
    <!-- //-----------스타의 상품 조회----------------------------// -->


    <!-- //-----------상품 삭제----------------------------// -->
    <hr>
    <br>
    <hr>
    <div>
      <h1>상품 삭제</h1>
      <form @submit.prevent="submitFormgetdelete">
        <input type="text" v-model="inputProductIdDelete" placeholder="삭제할 상품ID 입력">
        <button type="submit">삭제하기</button>
      </form>
      <p>상품 상세 조회랑 같이 사용시 확인 가능</p>
    </div>
    <!-- //-----------상품 삭제----------------------------// -->



    <!-- //-----------상품 생성----------------------------// -->

    <hr>
    <br>
    <hr>
    <h1>상품 생성</h1>
    <form @submit.prevent="submitForm">
      <input type="text" v-model="productcreate.starName" placeholder="스타 이름">
      <input type="text" v-model="productcreate.name" placeholder="상품 이름">
      <input type="number" v-model.number="productcreate.amount" placeholder="수량">
      <input type="number" v-model.number="productcreate.price" placeholder="가격">
      <textarea v-model="productcreate.content" placeholder="상품 설명"></textarea>
      <input type="file" ref="files" multiple>
      <button type="submit">생성</button>
    </form>
    <!-- //-----------상품 생성----------------------------// -->


    <!-- //-----------상품 수정 이미지제외----------------------------// -->

    <hr>
    <br>
    <hr>

    <h1>상품 정보 업데이트</h1>
    <form @submit.prevent="submitFormupdate">
      <div class="d-flex">
        <p>상품Id</p><input type="number" v-model="productIdupdate" placeholder="상품 ID">
      </div>
      <input type="text" v-model="productupdate.name" placeholder="상품 이름">
      <div class="d-flex">
        <p>수량</p>
        <input type="number" v-model.number="productupdate.amount" placeholder="수량">
      </div>
      <div class="d-flex">
        <p>가격</p>
        <input type="number" v-model.number="productupdate.price" placeholder="가격">
      </div>
      <input type="text" v-model="productupdate.imageUrl" placeholder="don't touch this holder">
      <textarea v-model="productupdate.content" placeholder="상품 설명"></textarea>
      <label>
        사용 가능:
        <input type="checkbox" v-model="productupdate.isAvailable">
      </label>
      <button type="submit">업데이트</button>
    </form>

    <!-- //-----------상품 수정 이미지제외----------------------------// -->


    <!-- //-----------상품 이미지 수정----------------------------// -->
    <hr>
    <br>
    <hr>

    <h1>상품 이미지 업데이트</h1>
    <form @submit.prevent="submitFormimageupdate">
      <input type="number" v-model="productIdimageupdate" placeholder="상품 ID">
      <input type="text" v-model="existingImageUrl" placeholder="기존 이미지 URL">
      <input type="file" ref="fileInputimageupdate">
      <button type="submit">업데이트</button>
    </form>

    <!-- //-----------상품 이미지 수정----------------------------// -->
  </main>
</template>
















<script setup lang="ts">

import { ref } from 'vue';
import axios from 'axios';

// ----------상품 상세조회----------------------------//

const productgetById = ref(null);
const inputProductId = ref(0);

const getProductById = async (productId: number) => {
  try {
    const response = await axios.get(`http://localhost:8080/product/getById/${productId}`);
    if (response.data.resultCode === 'SUCCESS') {
      productgetById.value = response.data;
    } else {
      console.error(response.data.message);
      console.log(response.data)
      productgetById.value = response.data
    }
  } catch (error) {
    console.error('상품 조회 중 오류 발생', error);
  }
};

const submitFormGetProductById = () => {
  getProductById(inputProductId.value);
};
// ----------상품 상세 조회----------------------------//




//-----------전체 상품 조회----------------------------//

const productgetByStarName = ref(null);
const inputStarName = ref('');

const getProductsByStarName = async (starName: string) => {
  try {
    const response = await axios.get(`http://localhost:8080/product/getByStarName/${starName}`);
    if (response.data.resultCode === 'SUCCESS') {
      productgetByStarName.value = response.data.data;
    } else {
      console.error(response.data.message);
      productgetByStarName.value = response.data.data;
    }
  } catch (error) {
    console.error('스타의 상품 목록 조회 중 오류 발생', error);
  }
};

const submitFormgetByStarName = () => {
  getProductsByStarName(inputStarName.value);
};

//-----------전체 상품 조회----------------------------//



//-----------상품 삭제----------------------------//

const productdelete = ref(null);
const inputProductIdDelete = ref(1);

const getProductdelete = async (productId: number) => {
  try {
    const response = await axios.post(`http://localhost:8080/product/delete/${productId}`);
    if (response.data.resultCode === 'SUCCESS') {
      productdelete.value = response.data.data;
    } else {
      console.error(response.data.message);
      productdelete.value = response.data.data;
    }
  } catch (error) {
    console.error('스타의 상품 목록 조회 중 오류 발생', error);
  }
};

const submitFormgetdelete = () => {
  getProductdelete(inputProductIdDelete.value);
};
//-----------상품 삭제----------------------------//


//-----------상품 생성----------------------------//

const productcreate = ref({
  starName: '',
  name: '',
  amount: 0,
  price: 0,
  content: ''
});
const files = ref(null);

const createProduct = async (productInfo: any, fileInputs: FileList) => {
  try {
    const formData = new FormData();
    formData.append('productDto', JSON.stringify(productInfo));
    for (let i = 0; i < fileInputs.length; i++) {
      formData.append('files', fileInputs[i]);
    }

    const response = await axios.post('http://localhost:8080/product/create', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    if (response.status === 200) {
      alert('상품이 성공적으로 생성되었습니다.');
    } else {
      alert('상품 생성에 실패했습니다.');
    }
  } catch (error) {
    console.error('상품 생성 중 오류 발생', error);
  }
};

const submitForm = () => {
  if (files.value && files.value.files.length > 0) {
    createProduct(productcreate.value, files.value.files);
  } else {
    alert('파일을 선택해주세요.');
  }
};

//-----------상품 생성----------------------------//


//-----------상품 수정 이미지제외----------------------------//


const productIdupdate = ref(0);
const productupdate = ref({
  starId: 99,
  starName: "nono",
  name: '',
  amount: 0,
  price: 0,
  imageUrl: [],
  content: '',
  isAvailable: false
});

const updateProduct = async (productIdupdate: number, updatedProductDto: any) => {
  try {
    const response = await axios.post(`http://localhost:8080/product/update/${productIdupdate}`, updatedProductDto);
    if (response.data === 'SUCCESS') {
      alert('상품이 성공적으로 업데이트되었습니다.');
    } else {
      alert('상품 업데이트에 실패했습니다.');
      console.log(response.data)
    }
  } catch (error) {
    console.error('상품 업데이트 중 오류 발생', error);
  }
};

const submitFormupdate = () => {
  updateProduct(productIdupdate.value, productupdate.value);
};

//-----------상품 수정 이미지제외----------------------------//






//-----------상품 이미지 수정----------------------------//

const productIdimageupdate = ref(0);
const existingImageUrl = ref('');
const fileInputimageupdate = ref();

const updateProductImage = async (productIdimageupdate: number, existingImageUrl: string, file: File) => {
  try {
    const formData = new FormData();
    formData.append('existingImageUrl', existingImageUrl);
    formData.append('file', file);

    const response = await axios.post(`http://localhost:8080/product/updateImg/${productIdimageupdate}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    if (response.status === 200) {
      alert('상품 이미지가 성공적으로 업데이트되었습니다.');
    } else {
      alert('상품 이미지 업데이트에 실패했습니다.');
    }
  } catch (error) {
    console.error('상품 이미지 업데이트 중 오류 발생', error);
  }
};

const submitFormimageupdate = () => {
  if (fileInputimageupdate.value && fileInputimageupdate.value.files.length > 0) {
    updateProductImage(productIdimageupdate.value, existingImageUrl.value, fileInputimageupdate.value.files[0]);
  } else {
    alert('이미지 파일을 선택해주세요.');
  }
};

//-----------상품 이미지 수정----------------------------//


//-----------상품 개별 이미지 수정, 삭제, 추가 ----------------------------//
const uploadImage = async (productId: number) => {
  try {
    // 파일 선택하기
    const file = await selectFile2();
    if (!file) return;

    // FormData 생성
    const formData = new FormData();
    formData.append('file', file);

    // Axios 요청 보내기
    const response = await axios.post(`http://localhost:8080/s3/upload/${productId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    // 업로드 후 결과 처리 (예: 새로고침, 상태 업데이트 등)
    console.log('업로드 성공:', response.data);
    getProductById(productId)
  } catch (error) {
    console.error('업로드 실패:', error);
  }
};

// 파일 선택 함수
const selectFile2 = () => {
  return new Promise<File | null>((resolve) => {
    const input = document.createElement('input');
    input.type = 'file';
    input.onchange = () => {
      resolve(input.files ? input.files[0] : null);
    };
    input.click();
  });
};








const updateImage = async (productId: number, imageId: number) => {
  try {
    // 파일 선택하기
    const file = await selectFile();
    if (!file) return;

    // FormData 생성
    const formData = new FormData();
    formData.append('file', file);

    // Axios 요청 보내기
    const response = await axios.post(`http://localhost:8080/s3/update/${productId}/${imageId}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

    getProductById(productId)
  } catch (error) {
    console.error('업데이트 실패:', error);
  }
};

const deleteImage = async (imageId: number, productId: number) => {
  try {
    await axios.post(`http://localhost:8080/s3/delete/${imageId}`);
    getProductById(productId)
  } catch (error) {
    console.error('이미지 삭제 실패:', error);
  }
};

const selectFile = () => {
  return new Promise<File | null>((resolve) => {
    const input = document.createElement('input');
    input.type = 'file';
    input.onchange = () => {
      resolve(input.files ? input.files[0] : null);
    };
    input.click();
  });
};
//-----------상품 개별 이미지 수정 및 삭제----------------------------//

</script>