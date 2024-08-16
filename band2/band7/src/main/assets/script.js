const axios = require('axios');

const ACCESS_TOKEN = "ZQAAAZWzHq9KcSLxOT3AatmkPY7aOIb6eH_pye6bGQzMhFFFg19ie7dENad7EHTcxCJm7thWqoXIYLKNZnMTXCEdv8TTRiKaq2jIGnp-KBKRP-mH";
const HOST = "https://openapi.band.us";
const POST_API = "/v2/band/posts";
const COMMENT_API = "/v2/band/post/comment/create";

// parameters
const BAND_KEY = "AACsu29d6yYC7v3fNw_ggj_o"; // Replace with the actual band key
const BODY = "좋아요";

async function fetchPosts() {
  try {
    const response = await axios.get(`${HOST}${POST_API}`, {
      headers: {
        "Content-Type": "application/json"
      },
      params: {
        access_token: ACCESS_TOKEN,
        band_key: BAND_KEY,
      }
    });

  

    const posts = response.data.result_data.items;

    if (posts.length > 0) {
      return posts[0].post_key; // 첫 번째 포스트의 post_key 반환
    } else {
      console.log('No posts found');
      return null;
    }

  } catch (error) {
    if (error.response) {
      console.error('Error response:', error.response.data);
    } else if (error.request) {
      console.error('Error request:', error.request);
    } else {
      console.error('Error message:', error.message);
    }
    return null;
  }
}

async function postComment(postKey) {
  try {
    const response = await axios.post(`${HOST}${COMMENT_API}`, null, {
      params: {
        access_token: ACCESS_TOKEN,
        band_key: BAND_KEY,
        post_key: postKey,
        body: BODY,
      },
      headers: {
        "Content-Type": "application/json",
      },
    });

    console.log(response.data); // response!

  } catch (error) {
    console.error("Error!", error.response ? error.response.data : error.message);
  }
}

async function main() {
  const postKey = await fetchPosts();
  if (postKey) {
    await postComment(postKey);
  }
}

main();