<template lang="html">
  <footer id="mt-footer" class="footer">
    <div class="container">
      <div class="columns">
        <div class="column is-4">
          <p class="footer-link-title has-text-weight-bold">واویشکا</p>
          <!-- Loop through the navigation items -->
          <a class="navbar-item nav-home is-active" href="https://biron.bironthemes.com/">درباره واویشکا</a>
          <a class="navbar-item nav-style-guide" href="https://biron.bironthemes.com/style-guide/">قوانین و مقررات</a>
          <a class="navbar-item nav-features" href="https://biron.bironthemes.com/features/">حریم خصوصی</a>
          <a class="navbar-item nav-tech" href="https://biron.bironthemes.com/tag/technology/">تماس با ما</a>
          <a class="navbar-item nav-web" href="https://biron.bironthemes.com/tag/web/">راهنمای خرید</a>

        </div>
        <div class="column is-4">
          <p class="footer-link-title has-text-weight-bold">واویشکا در شبکه های اجتماعی</p>
          <div class="social-media">
            <a class="social-link facebook" href="https://www.facebook.com/bironthemes" target="_blank" title="Facebook" aria-label="Facebook">
              <div class="icon facebook">
                <svg class="icon__svg">
                  <use xlink:href="#facebook"></use>
                </svg>
              </div>
              <p>
                <i class="fab fa-facebook"></i>
                فیسبوک</p>
            </a>
            <a class="social-link twitter" href="https://twitter.com/bironthemes" target="_blank" title="Twitter" aria-label="Twitter">
              <div class="icon twitter">
                <svg class="icon__svg">
                  <use xlink:href="#twitter"></use>
                </svg>
              </div>
              <p>
                <i class="fab fa-twitter"></i>
              توییتر</p>
            </a>
            <a class="social-link instagram" href="https://instagram.com/bironthemes" target="_blank" title="Instagram" aria-label="Instagram">
              <div class="icon instagram">
                <svg class="icon__svg">
                  <use xlink:href="#instagram"></use>
                </svg>
              </div>
              <p>
                <i class="fab fa-instagram"></i>
              اینستاگرام</p>
            </a>
            <a class="social-link linkedin" href="https://linkedin.com" target="_blank" title="Linkedin" aria-label="Linkedin">
              <div class="icon linkedin">
                <svg class="icon__svg">
                  <use xlink:href="#linkedin"></use>
                </svg>
              </div>
              <p>
                <i class="fab fa-linkedin"></i>
              لینکداین</p>
            </a>
          </div>
        </div>
        <div class="column is-4">
          <p class="footer-link-title has-text-weight-bold">اشتراک خبرنامه</p>
          <p class="footer-text">جهت اطلاع از آخرین آموزش ها آدرس ایمیل خود را وارد کنید و در خبرنامه مشترک شوید</p>
          <form method="post" action="/subscribe/" class="">
            <input class="confirm" type="hidden" name="confirm"><input class="location" type="hidden" name="location" value="https://biron.bironthemes.com/"><input class="referrer" type="hidden" name="referrer" value="">

            <div class="form-group">
              <input class="input is-rounded" type="email" name="email" placeholder="ایمیل خود را وارد کنید">
            </div>
            <button class="button is-primary is-rounded has-text-weight-bold" type="submit"><span>مشترک شوید</span></button>
          </form>

        </div>
      </div>
      <hr>
      <div class="footer-bottom has-text-centered">
        کلیه حقوق این وب سایت متعلق به گروه <a href="http://vavishka.piana.ir">Piana</a> است.
      </div>
    </div>
  </footer>
</template>

<script lang="js">

  export default  {
    name: 'AppFooter',
    props: [],
    mounted () {

    },
    data () {
      return {

      }
    },
    methods: {

    },
    computed: {

  }
}

</script>

<style scoped lang="css">
  .footer {
    margin-top: 16px;
    padding: 2em 1.5em
  }

  .footer-link-title {
    margin-bottom: 1em
  }

  .footer .navbar-item {
    padding: 0;
    margin-bottom: .25em
  }

  .footer .footer-text,.footer .form-group {
    margin-bottom: 1em
  }

  .social-link {
    display: -webkit-box;
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    margin-bottom: .5em
  }

  .social-link .icon {
    margin-right: .5em
  }

  .social-link p {
    line-height: 25px
  }

  .has-text-weight-semibold {
    font-weight: 600!important
  }

  .has-text-weight-bold {
    font-weight: 700!important
  }

  hr {
    border: none;
    display: block;
    height: 2px;
    margin: 1.5rem 0
  }

  code, hr {
    background-color: #f5f5f5;
  }

</style>
