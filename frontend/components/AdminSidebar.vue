<template>
  <div class="sidenav navbar navbar-vertical fixed-left navbar-expand-xs navbar-light bg-white">
    <div class="scrollbar-inner ps ps--active-y">
      <div class="navbar-inner">
        <ul class="navbar-nav">
          <li class="nav-item">
            <nuxt-link class="sidebar-menu-item" to="/admin/dashboard">
              <i class="fas fa-clipboard text-primary" />
              <span class="nav-link-text">Dashboard</span>
            </nuxt-link>
          </li>
          <li class="nav-item">
            <nuxt-link class="sidebar-menu-item" to="/admin/media">
              <i class="ni ni-shop text-primary" />
              <span class="nav-link-text">Media</span>
            </nuxt-link>
          </li>
          <li class="nav-item">
            <span v-b-toggle.product-collapse class="sidebar-menu-item nav-link">
              <i class="ni ni-shop text-primary" />
              <span class="nav-link-text">Products</span>
              <b class="caret" />
            </span>
            <b-collapse id="product-collapse">
              <ul class="nav">
                <li class="nav-item">
                  <nuxt-link class="nav-link" to="/admin/product/planners/all">
                    <i class="ni ni-shop text-primary" />
                    <span class="nav-link">All Planners</span>
                  </nuxt-link>
                </li>
                <li class="nav-item">
                  <nuxt-link class="nav-link" to="/admin/product/logos/all">
                    <i class="ni ni-shop text-primary" />
                    <span class="nav-link">All Logos</span>
                  </nuxt-link>
                </li>
                <li class="nav-item">
                  <nuxt-link class="nav-link" to="/admin/product/new_planner">
                    <i class="ni ni-shop text-primary" />
                    <span class="nav-link">New Planner</span>
                  </nuxt-link>
                </li>
                <li class="nav-item">
                  <nuxt-link class="nav-link" to="/admin/product/new_logo">
                    <i class="ni ni-shop text-primary" />
                    <span class="nav-link">New Logo</span>
                  </nuxt-link>
                </li>
              </ul>
            </b-collapse>
          </li>
          <li class="nav-item">
            <span v-b-toggle.order-collapse class="sidebar-menu-item nav-link">
              <i class="ni ni-shop text-primary" />
              <span class="nav-link-text">Orders</span>
              <b class="caret" />
            </span>
            <b-collapse id="order-collapse">
              <ul class="nav">
                <li class="nav-item">
                  <nuxt-link class="nav-link" to="/admin/order/all">
                    <i class="ni ni-shop text-primary" />
                    <span class="nav-link-text">All orders</span>
                  </nuxt-link>
                </li>
                <li class="nav-item">
                  <nuxt-link class="nav-link" to="/admin/order/vat">
                    <i class="ni ni-shop text-primary" />
                    <span class="nav-link-text">VAT Statistic</span>
                  </nuxt-link>
                </li>
              </ul>
            </b-collapse>
          </li>
          <li class="nav-item">
            <span v-b-toggle.discount-collapse class="sidebar-menu-item nav-link">
              <i class="ni ni-shop text-primary" />
              <span class="nav-link-text">Discounts</span>
              <b class="caret" />
            </span>
            <b-collapse id="discount-collapse">
              <ul class="nav">
                <li class="nav-item">
                  <nuxt-link class="nav-link" to="/admin/discount/set">
                    <i class="ni ni-shop text-primary" />
                    <span class="nav-link-text">Set Discount</span>
                  </nuxt-link>
                </li>
                <li class="nav-item">
                  <nuxt-link class="nav-link" to="/admin/discount/all">
                    <i class="ni ni-shop text-primary" />
                    <span class="nav-link-text">Running Discounts</span>
                  </nuxt-link>
                </li>
              </ul>
            </b-collapse>
          </li>
          <li class="nav-item">
            <nuxt-link class="sidebar-menu-item" to="/admin/subscribers">
              <i class="ni ni-shop text-primary" />
              <span class="nav-link-text">Subscribers</span>
            </nuxt-link>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>
.sidenav {
    left: 0;
    border-width: 0 1px 0 0;
    max-width: 250px!important;
    display: block;
    position: fixed;
    top: 0;
    bottom: 0;
    width: 100%;
    overflow-y: auto;
    padding-left: 0;
    padding-right: 0;
    background-color: #f6f9fc;
    border-color: rgba(0,0,0,.05);
    z-index: 1050;
    transition: all .15s ease;
    padding-top: 0;
    border-style: solid;
    box-shadow: 0 0 2rem 0 rgba(136,152,170,.15);
    flex-wrap: wrap;
    align-items: center;
    justify-content: space-between;
}
.scrollbar-inner {
    height: 100%;
    overflow: hidden!important;
    overflow-anchor: none;
    -ms-overflow-style: none;
    touch-action: auto;
    -ms-touch-action: auto;
}
.navbar-inner {
    padding-left: 1.5rem;
    padding-right: 1.5rem;
}
.navbar-nav {
    flex-direction: column;
    margin-left: -1.5rem;
    margin-right: -1.5rem;
    display: flex;
    padding-left: 0;
    margin-bottom: 0;
    list-style: none;
}
.nav-item {
    margin-top: 2px;
}
.sidebar-menu-item, .btn {
    background: #f6f9fc;
    margin-right: .5rem;
    margin-left: .5rem;
    padding-left: 1rem;
    padding-right: 1rem;
    border-radius: .375rem;
    padding: .675rem 1.5rem;
    position: relative;
    color: rgba(0,0,0,.9);
    display: flex;
    align-items: center;
    font-size: .875rem;
    font-weight: 500;
    text-decoration: none;
    cursor: pointer;
}
.collapse {
    height: auto;
    transition: all .15s ease;
}
.collapse ul.nav {
    display: flex;
    flex-wrap: wrap;
    padding-left: 0;
    margin-bottom: 0;
    list-style: none;
    flex-direction: column!important;
}
.nav-link-text {
    font-size: .8125rem;
    color: rgba(0,0,0,.9);
    font-weight: 500;
    cursor: pointer;
}
.nav-link {
    padding-top: .45rem;
    padding-bottom: .45rem;
    padding-left: 3.5rem;
    padding: .675rem 1.5rem;
    font-size: .8125rem;
    display: flex;
    align-items: center;
    font-weight: 500;
    color: rgba(0,0,0,.6);
}
.nav-link:hover {
    text-decoration: none;
}
.nav-link[aria-expanded=true]:after {
    color: #5e72e4;
    -webkit-transform: rotate(90deg);
    transform: rotate(90deg);
    display: inline-block;
    font-style: normal;
    -webkit-font-feature-settings: normal;
    font-feature-settings: normal;
    font-variant: normal;
    text-rendering: auto;
    -webkit-font-smoothing: antialiased;
    font-family: Font Awesome\ 5 Free;
    font-weight: 700;
    content: "\f105";
    margin-left: auto;
    color: #ced4da;
    transition: all .15s ease;
}
.nav-link[aria-expanded=false]:after {
    display: inline-block;
    font-style: normal;
    -webkit-font-feature-settings: normal;
    font-feature-settings: normal;
    font-variant: normal;
    text-rendering: auto;
    -webkit-font-smoothing: antialiased;
    font-family: Font Awesome\ 5 Free;
    font-weight: 700;
    content: "\f105";
    margin-left: auto;
    color: #ced4da;
    transition: all .15s ease;
}
.btn {
    background-color: transparent;
    border-color: transparent;
}
</style>
