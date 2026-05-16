$(function () {

  $('#friendgroup').on('click', function () {

    $.ajax({
      url: '/weChat_v1/Friendgroup',
      type: 'POST',
      data: {
        'content': $('#content').val()
      }
    })
      // Ajaxリクエストが成功した時発動
      .done((data) => {
        $('#res').html(data);

      })

  });
});