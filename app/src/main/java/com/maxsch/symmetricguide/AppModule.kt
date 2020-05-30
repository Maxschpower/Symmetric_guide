package com.maxsch.symmetricguide

import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.maxsch.symmetricguide.App.Companion.convertStreamToString
import com.maxsch.symmetricguide.data.datasource.Database
import com.maxsch.symmetricguide.data.datasource.material.MaterialDao
import com.maxsch.symmetricguide.data.datasource.material.MaterialDataSource
import com.maxsch.symmetricguide.data.datasource.session.SessionDataSource
import com.maxsch.symmetricguide.data.datasource.settings.SettingsDataSource
import com.maxsch.symmetricguide.data.repository.material.MaterialRepositoryImpl
import com.maxsch.symmetricguide.data.repository.session.SessionRepositoryImpl
import com.maxsch.symmetricguide.data.repository.settings.SettingsRepositoryImpl
import com.maxsch.symmetricguide.data.repository.user.UserRepositoryImpl
import com.maxsch.symmetricguide.entity.material.Material
import com.maxsch.symmetricguide.entity.material.repository.MaterialRepository
import com.maxsch.symmetricguide.entity.session.repository.SessionRepository
import com.maxsch.symmetricguide.entity.settings.SettingsRepository
import com.maxsch.symmetricguide.entity.user.repository.UserRepository
import com.maxsch.symmetricguide.presentation.login.LoginViewModel
import com.maxsch.symmetricguide.presentation.materials.MaterialEditorViewModel
import com.maxsch.symmetricguide.presentation.materials.MaterialsViewModel
import com.maxsch.symmetricguide.presentation.materials.list.MaterialsListViewModel
import com.maxsch.symmetricguide.presentation.register.RegisterViewModel
import com.maxsch.symmetricguide.presentation.settings.SettingsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.io.InputStream


val appModule = module {
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            androidContext().getString(R.string.preferences_name),
            Context.MODE_PRIVATE
        )
    }
    single { get<Database>().userDao() }
    single { get<Database>().materialDao() }
    single { SessionDataSource(get()) }
    single { MaterialDataSource() }
    single<SessionRepository> { SessionRepositoryImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<MaterialRepository> { MaterialRepositoryImpl(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { RegisterViewModel(get(), get()) }
    viewModel { MaterialsListViewModel(get()) }
    viewModel { (id: Int) -> MaterialsViewModel(get(), id) }
    viewModel { (id: Int) -> MaterialEditorViewModel(get(), id) }
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
    single { SettingsDataSource(get()) }
    single { FontSizeController(get()) }
    single {
        Room.databaseBuilder(
            androidApplication(),
            Database::class.java,
            androidContext().getString(R.string.user_database_name)
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val am: AssetManager = androidApplication().assets
                    val aesStream: InputStream = am.open("html/aes.html")
                    val desStream: InputStream = am.open("html/des.html")
                    val gostStream: InputStream = am.open("html/gost.html")
                    val aes: String = convertStreamToString(aesStream)
                    val des: String = convertStreamToString(desStream)
                    val gost: String = convertStreamToString(gostStream)
                    aesStream.close()

                    var a: Disposable? = null
                    a = get<MaterialDao>().addMaterials(
                        listOf(
                            Material(
                                0,
                                "AES",
                                aes,
                                "История AES. В 1997 г. Национальный институт стандартов и технологий США (NIST)\n" +
                                        "объявил о проведении конкурса по замене стандарта DES. На конкурс могли быть\n" +
                                        "присланы алгоритмы шифрования, разработанные как организациями, так и частными\n" +
                                        "лицами в любой стране. Алгоритм — победитель этого конкурса должен был стать новым\n" +
                                        "стандартом блочного симметричного шифрования США."
                            ),
                            Material(
                                0,
                                "DES",
                                des,
                                "Общая схема алгоритма. Алгоритм шифрования DES (Data Encryption Standard) был\n" +
                                        "опубликован в 1977 г. и предназначался для защиты важной, но несекретной информации\n" +
                                        "в государственных и коммерческих организациях США. DES основан на схеме Фейстеля.\n" +
                                        "Реализованные в DES идеи были во многом позаимствованы в более ранней разработке\n" +
                                        "корпорации IBM — шифре «Люцифер» (в IBM работал X. Фейстель, автор одноименной\n" +
                                        "схемы). Но для своего времени «Люцифер» был слишком сложным, и его реализации\n" +
                                        "отличались низким быстродействием."
                            ),
                            Material(
                                0,
                                "Алгоритм ГОСТ 28147-89",
                                gost,
                                "Общая схема алгоритма. Алгоритм, описанный ГОСТ 28147—89 «Системы обработки\n" +
                                        "информации. Защита криптографическая. Алгоритм криптографического преобразования»,\n" +
                                        "является отечественным стандартом симметричного шифрования (до 1 января 2016 г.) и\n" +
                                        "обязателен для реализации в сертифицированных средствах криптографической защиты\n" +
                                        "информации, применяемых в государственных информационных системах и, в некоторых\n" +
                                        "случаях, в коммерческих системах. Сертификация средств криптографической защиты\n" +
                                        "информации требуется для защиты сведений, составляющих государственную тайну РФ, и\n" +
                                        "сведений, конфиденциальность которых требуется обеспечить согласно действующему\n" +
                                        "законодательству. Также в Российской Федерации применение алгоритма ГОСТ 28147—89\n" +
                                        "рекомендовано для защиты банковских информационных систем."
                            )
                        )
                    )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            a?.dispose()
                            Log.e("succ", "succ")
                        }, {
                            Log.e("fail", "blyatb")
                        })

                }
            })
            .fallbackToDestructiveMigration()
            .build()
    }
}