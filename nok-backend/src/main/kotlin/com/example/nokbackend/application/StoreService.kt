package com.example.nokbackend.application

import com.example.nokbackend.domain.BaseEntityUtil
import com.example.nokbackend.domain.authentication.Authentication
import com.example.nokbackend.domain.member.Member
import com.example.nokbackend.domain.member.MemberRepository
import com.example.nokbackend.domain.store.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreService(
    private val storeRepository: StoreRepository,
    private val storeQueryRepository: StoreQueryRepository,
    private val memberRepository: MemberRepository,
    private val menuRepository: MenuRepository,
    private val storeImageRepository: StoreImageRepository,
    private val authenticationService: AuthenticationService
) {

    fun registerStore(registerStoreRequest: RegisterStoreRequest): Long {
        check(registerStoreRequest.owner.role == Member.Role.STORE) { "상점 주인으로만 등록할 수 있습니다" }

        authenticationService.confirmAuthentication(
            ConfirmAuthenticationRequest(
                registerStoreRequest.owner.authenticationId,
                registerStoreRequest.owner.email,
                registerStoreRequest.owner.authenticationCode
            ),
            Authentication.Type.REGISTER
        )

        val owner = saveOwner(registerStoreRequest.owner)

        val store = saveStore(registerStoreRequest, owner)

        saveStoreImages(registerStoreRequest.storeImages, store)

        saveMenus(registerStoreRequest.menus, store)

        return store.id
    }

    private fun saveMenus(commonMenuRequests: List<RegisterMenuRequest>, store: Store) {
        commonMenuRequests.forEach {
            saveMenu(it, store)
        }
    }

    private fun saveMenu(it: RegisterMenuRequest, store: Store) {
        val menu = it.toEntity(store)
        menuRepository.save(menu)
    }


    private fun saveOwner(registerStoreRequest: RegisterMemberRequest): Member {
        val owner = registerStoreRequest.toEntity()
        return memberRepository.save(owner)
    }

    private fun saveStore(registerStoreRequest: RegisterStoreRequest, owner: Member): Store {
        val store = registerStoreRequest.toEntity(owner.id)
        return storeRepository.save(store)
    }

    private fun saveStoreImages(storeImages: List<String>, store: Store) {
        storeImages.forEach {
            val storeImage = StoreImage(store, it, StoreImage.Status.ACTIVE)
            storeImageRepository.save(storeImage)
        }
    }

    fun findByCondition(findStoreCondition: FindStoreCondition): List<StoreResponse> {
        return storeQueryRepository.findByCondition(findStoreCondition)
            .map { StoreResponse(it) }
    }

    fun findStoreInformation(storeId: Long): StoreDetailResponse {
        val store = storeRepository.findByIdCheck(storeId)

        check(store.status == Store.Status.ACTIVE) { "삭제되거나 등록대기 상태인 점포입니다" }

        val storeImages = storeImageRepository.findByStoreAndStatus(store, StoreImage.Status.ACTIVE)

        val menus = menuRepository.findByStoreAndStatus(store, Menu.Status.ACTIVE)

        return StoreDetailResponse(store, storeImages, menus)
    }

    fun updateStoreInformation(member: Member, storeId: Long, updateStoreInformationRequest: UpdateStoreInformationRequest) {
        val store = storeRepository.findByIdCheck(storeId)

        check(store.ownerId == member.id) { "본인의 상점만 수정할 수 있습니다" }

        store.updateStoreInformation(updateStoreInformationRequest)
    }

    fun updateMenu(member: Member, storeId: Long, updateMenuRequests: List<UpdateMenuRequest>) {
        val store = storeRepository.findByIdCheck(storeId)
        check(store.ownerId == member.id) { "본인의 상점이 아닙니다" }

        val menuIds = updateMenuRequests.map { it.id }
        val menus = menuRepository.findAllById(menuIds)
        val menuMap = BaseEntityUtil<Menu>().mapById(menus)

        updateMenuRequests.forEach {
            val menu = menuMap[it.id]!!
            check(menu.store == store) { "해당 상점의 메뉴만 수정할 수 있습니다" }
            menu.update(it)
        }
    }

    fun deleteMenu(member: Member, storeId: Long, deleteMenuRequest: List<DeleteMenuRequest>) {
        val store = storeRepository.findByIdCheck(storeId)
        check(store.ownerId == member.id) { "본인의 상점이 아닙니다" }

        val menuIds = deleteMenuRequest.map { it.id }

        menuRepository.findAllById(menuIds).forEach {
            check(it.store == store) { "해당 상점의 메뉴만 수정할 수 있습니다" }
            it.inactive()
        }
    }
}