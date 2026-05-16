$(function () {

  $.ajax({
    url: '/weChat_v1/Peng',
    type: 'POST',
    data: {

    }
  })
    // Ajaxリクエストが成功した時発動
    .done((data) => {
      $('#res').html(data);

    })

});