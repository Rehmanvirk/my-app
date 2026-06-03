package com.app.calllogs.di.repository.facke

import android.R.attr.name
import com.app.calllogs.di.data.ConvertLeadToContact
import com.app.calllogs.di.data.ConvertLeadToDeal
import com.app.calllogs.di.data.CreateLeadRequest
import com.app.calllogs.di.data.DynamicFields
import com.app.calllogs.di.data.Lead
import com.app.calllogs.di.data.LeadDetailsResponse
import com.app.calllogs.di.data.LeadResponse
import com.app.calllogs.di.data.LeadStatus
import com.app.calllogs.di.network.ApiResult
import com.app.calllogs.di.repository.LeadsRepository
import javax.inject.Inject

class FakeLeadsRepository @Inject constructor()  {
    //    override suspend fun getLeads(id : String): List<Lead> = listOf(
//        Lead("1", "John Doe", LeadStatus.NEW, "2h ago", "JD"),
//        Lead("2", "Sarah Jenkins", LeadStatus.CONTACTED, "Oct 24", "SJ"),
//        Lead("3", "Michael Chen", LeadStatus.HOT, "4h ago", "MC"),
//        Lead("4", "Emma Wilson", LeadStatus.IN_PROGRESS, "Yesterday", "EW"),
//        Lead("5", "David Miller", LeadStatus.NEW, "1d ago", "DM"),
//        Lead("6", "Jessica Vance", LeadStatus.CLOSED, "2d ago", "JV"),
//    )
//    override suspend fun getLeads(id: String): ApiResult<LeadResponse> {
//        return ApiResult.Success(data = LeadResponse(message = "", count = 6, leads =
//            listOf(
//        Lead(
//            "1", DynamicFields("", "John Doe", LeadStatus.AttemptedToContact.name,  "JD"),"2h ago"),
//        Lead("2",DynamicFields( "","Sarah Jenkins", LeadStatus.Contacted.name,  "SJ"),"Oct 24",),
//        Lead("3", DynamicFields("","Michael Chen", LeadStatus.LostLead.name,  "MC"),"4h ago",),
//        Lead("4",DynamicFields( "","Emma Wilson", LeadStatus.ContactInFuture.name, "EW"),"Yesterday", ),
//        Lead("5",DynamicFields( "","David Miller", LeadStatus.PreQualified.name, "DM"),"1d ago"),
//        Lead("6",DynamicFields( "","Jessica Vance", LeadStatus.NotContacted.name,  "JV"),"2d ago"))))
//    }
//
//    override suspend fun createLead(req: CreateLeadRequest): ApiResult<LeadResponse> {
//        return ApiResult.Success(data = LeadResponse(message = "", count = 6, leads =
//            listOf(
//                Lead(
//                    "1", DynamicFields("", "John Doe", LeadStatus.AttemptedToContact.name,  "JD"),"2h ago"),
//                Lead("2",DynamicFields( "","Sarah Jenkins", LeadStatus.Contacted.name,  "SJ"),"Oct 24",),
//                Lead("3", DynamicFields("","Michael Chen", LeadStatus.LostLead.name,  "MC"),"4h ago",),
//                Lead("4",DynamicFields( "","Emma Wilson", LeadStatus.ContactInFuture.name, "EW"),"Yesterday", ),
//                Lead("5",DynamicFields( "","David Miller", LeadStatus.PreQualified.name, "DM"),"1d ago"),
//                Lead("6",DynamicFields( "","Jessica Vance", LeadStatus.NotContacted.name,  "JV"),"2d ago"))))
//    }
//
//    override suspend fun getLeadDetails(id: String): ApiResult<LeadDetailsResponse> {
//        return ApiResult.Success(data = LeadDetailsResponse(message = "", lead =
//            Lead(
//                "1", DynamicFields("", "John Doe", LeadStatus.AttemptedToContact.name,  "JD"),"2h ago")))
//    }
//
//    override suspend fun convertLeadToContact(
//        id: String,
//        req: ConvertLeadToContact
//    ): ApiResult<LeadResponse> {
//        return ApiResult.Success(data = LeadResponse(message = "", count = 6, leads =
//            listOf(
//                Lead(
//                    "1", DynamicFields("", "John Doe", LeadStatus.AttemptedToContact.name,  "JD"),"2h ago"),
//                Lead("2",DynamicFields( "","Sarah Jenkins", LeadStatus.Contacted.name,  "SJ"),"Oct 24",),
//                Lead("3", DynamicFields("","Michael Chen", LeadStatus.LostLead.name,  "MC"),"4h ago",),
//                Lead("4",DynamicFields( "","Emma Wilson", LeadStatus.ContactInFuture.name, "EW"),"Yesterday", ),
//                Lead("5",DynamicFields( "","David Miller", LeadStatus.PreQualified.name, "DM"),"1d ago"),
//                Lead("6",DynamicFields( "","Jessica Vance", LeadStatus.NotContacted.name,  "JV"),"2d ago"))))
//    }
//
//    override suspend fun convertLeadToDeal(
//        id: String,
//        req: ConvertLeadToDeal
//    ): ApiResult<LeadResponse> {
//        return ApiResult.Success(data = LeadResponse(message = "", count = 6, leads =
//            listOf(
//                Lead(
//                    "1", DynamicFields("", "John Doe", LeadStatus.AttemptedToContact.name,  "JD"),"2h ago"),
//                Lead("2",DynamicFields( "","Sarah Jenkins", LeadStatus.Contacted.name,  "SJ"),"Oct 24",),
//                Lead("3", DynamicFields("","Michael Chen", LeadStatus.LostLead.name,  "MC"),"4h ago",),
//                Lead("4",DynamicFields( "","Emma Wilson", LeadStatus.ContactInFuture.name, "EW"),"Yesterday", ),
//                Lead("5",DynamicFields( "","David Miller", LeadStatus.PreQualified.name, "DM"),"1d ago"),
//                Lead("6",DynamicFields( "","Jessica Vance", LeadStatus.NotContacted.name,  "JV"),"2d ago"))))
//    }
}