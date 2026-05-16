/**
 * 
 */
$(function () {

  $.ajax({
    url: '/weChat_v1/Friends',
    type: 'POST',
    data: {

    }
  })
    // Ajaxリクエストが成功した時発動
    .done((data) => {
      $('#res').html(data);
      console.log(data);
    })
    // Ajaxリクエストが失敗した時発動
    .fail((data) => {
      $('#res').html(data);

    })

});